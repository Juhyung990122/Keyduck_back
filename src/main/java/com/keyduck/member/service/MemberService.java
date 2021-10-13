package com.keyduck.member.service;


import com.keyduck.exception.FileDownloadException;
import com.keyduck.exception.FileUploadException;
import com.keyduck.mapper.LoginMapper;
import com.keyduck.mapper.MemberMapper;
import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.LoginMemberDto;
import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.dto.MemberGetDto;
import com.keyduck.member.dto.MemberLoginDto;
import com.keyduck.member.img.FileUploadProperties;
import com.keyduck.member.repository.MemberRepository;
import com.keyduck.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	private final Path fileLocation;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	LoginMapper loginMapper;

	@Autowired
	public MemberService(FileUploadProperties prop,PasswordEncoder passwordEncoder,MemberRepository memberRepository) {
		this.memberRepository  = memberRepository;
		this.passwordEncoder = passwordEncoder;
		this.fileLocation = Paths.get(prop.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileLocation);
        }catch(Exception e) {
            throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }
	}


	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		return memberRepository.findById(Long.valueOf(memberId))
				.orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));
	}


	public MemberCreateDto signup(MemberCreateDto memberInfo) {
		memberRepository.findByEmail(memberInfo.getEmail()).ifPresent(e ->
		{
			throw new RuntimeException("이미 가입한 회원입니다.");
		});
		String rawPassword = memberInfo.getPassword();
		String encodedPassword = passwordEncoder.encode("{noop}"+(CharSequence)rawPassword);
		memberInfo.setPassword(encodedPassword);
		memberRepository.save(memberInfo.toEntity());
		return memberInfo;
	}

	public LoginMemberDto signin(MemberLoginDto loginMember, JwtTokenProvider jwtTokenProvider){
			Member member = memberRepository.findByEmail(loginMember.getEmail())
					.orElseThrow(()-> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));
			if(! passwordEncoder.matches("{noop}"+loginMember.getPassword(), member.getPassword())) {
				throw new RuntimeException("올바른 패스워드가 아닙니다.");
			}
			String accessToken = jwtTokenProvider.createToken(member.getUsername(), member.getRole());
			String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRole());
			member.setAccessToken(accessToken);
			member.setRefreshToken(refreshToken);
			memberRepository.save(member);
			LoginMemberDto loginedMemberInfoDto = loginMapper.toDto(member,accessToken,refreshToken);
			return loginedMemberInfoDto;
	}

	public String refreshToken(String refreshToken,JwtTokenProvider jwtTokenProvider){
		Member member = memberRepository.findByRefreshToken(refreshToken)
				.orElseThrow(()-> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));

		String accessToken = jwtTokenProvider.createToken(member.getUsername(), member.getRole());
		return accessToken;

	}


	public List<MemberGetDto> getMembers(){
		List<Member> members = memberRepository.findAll();
		List<MemberGetDto> memberListDto = new ArrayList<MemberGetDto>();
		for(int i = 0; i < members.size(); i++ ){
			memberListDto.add(memberMapper.toDto(members.get(i)));
		}
		return memberListDto;
	}

	public MemberGetDto getMemberDetail(Long memberId) {
		Member findMember = memberRepository.findById(memberId)
				.orElseThrow(()-> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));
		MemberGetDto findMemberDto = memberMapper.toDto(findMember);
		return findMemberDto;
	}

	public String getLeaveMember(String email) throws Exception {
		Member findMember = memberRepository.findByEmail(email)
				.orElseThrow(()-> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));
		memberRepository.delete(findMember);
		return "성공적으로 탈퇴되었습니다";
	}


	public MemberGetDto uploadFile(Long memberId, MultipartFile req) throws Exception {
		Member member  = memberRepository.findById(memberId).orElseThrow(Exception::new);
		String filename = StringUtils.cleanPath(req.getOriginalFilename());
		Path targetLocation = this.fileLocation.resolve(filename);
		Files.copy(req.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

		String profile_url = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("v1/uploads/")
				.path(filename)
				.toUriString();

		member.setProfile(profile_url);
		memberRepository.save(member);
		MemberGetDto memberDto = memberMapper.toDto(member);
		return memberDto;
	}

	public Resource downloadFile(String fileName,HttpServletRequest request) throws IOException {
		try{
			Path filePath = this.fileLocation.resolve(fileName).normalize();
			Resource resourceUrl = new UrlResource(filePath.toUri());
			if (resourceUrl.exists()) {
				return resourceUrl;
			}else {
				throw new FileDownloadException(fileName + "파일을 찾을 수 없습니다.");
			}

		}catch(MalformedURLException e) {
			throw new FileDownloadException(fileName+"파일을 찾을 수 없습니다.",e);
		}
	}
}