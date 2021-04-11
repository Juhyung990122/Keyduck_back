package com.keyduck.member.service;


import com.keyduck.exception.FileDownloadException;
import com.keyduck.exception.FileUploadException;
import com.keyduck.mapper.MemberMapper;
import com.keyduck.member.domain.Member;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	private final Path fileLocation;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	MemberMapper memberMapper;

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
	public UserDetails loadUserByUsername(String MemId) throws UsernameNotFoundException {
		return memberRepository.findById(Long.valueOf(MemId))
				.orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));
	}


	public MemberCreateDto signup(MemberCreateDto newmember) {
		memberRepository.findByEmail(newmember.getEmail()).ifPresent(e -> { throw new RuntimeException("이미 가입한 회원입니다.");});
		String rawPassword = newmember.getPassword();
		String encodedPassword = passwordEncoder.encode("{noop}"+(CharSequence)rawPassword);
		newmember.setPassword(encodedPassword);
		memberRepository.save(newmember.toEntity());
		return newmember;
	}


	public String signin(MemberLoginDto loginmember,JwtTokenProvider jwtTokenProvider){
			Member member = memberRepository.findByEmail(loginmember.getEmail()).orElseThrow(()-> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));
			if(! passwordEncoder.matches("{noop}"+loginmember.getPassword(), member.getPassword())) {
				throw new RuntimeException("올바른 패스워드가 아닙니다.");
			}
			return jwtTokenProvider.createToken(member.getUsername(), member.getRole());
	}

	public List<MemberGetDto> getMembers(){
		List<Member> members = memberRepository.findAll();
		List<MemberGetDto> memList_dto = new ArrayList<MemberGetDto>();
		for(int i = 0; i < members.size(); i++ ){
			memList_dto.add(memberMapper.toDto(members.get(i)));
		}
		return memList_dto;
	}

	public MemberGetDto getMemberDetail(Long id) {
		Member findMem = memberRepository.findById(id).orElseThrow(()-> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));
		MemberGetDto findMem_dto = memberMapper.toDto(findMem);
		return findMem_dto;
	}

	public String getLeaveMember(String email) throws Exception {
		Member findMem = memberRepository.findByEmail(email).orElseThrow(()-> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));
		memberRepository.delete(findMem);
		return "성공적으로 탈퇴되었습니다";
	}


	public MemberGetDto uploadFile(Long mem_id, MultipartFile req) throws Exception {
		Member member  = memberRepository.findById(mem_id).orElseThrow(Exception::new);
		String filename = StringUtils.cleanPath(req.getOriginalFilename());
		Path targetLocation = this.fileLocation.resolve(filename);
		Files.copy(req.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

		String profile_url = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("v1/uploads/")
				.path(filename)
				.toUriString();

		member.setProfile(profile_url);
		memberRepository.save(member);
		MemberGetDto mem_dto = memberMapper.toDto(member);
		return mem_dto;
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