package com.keyduck.member.service;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.keyduck.exception.FileDownloadException;
import com.keyduck.exception.FileUploadException;
import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.dto.MemberLoginDto;

import com.keyduck.member.img.FileUploadProperties;
import com.keyduck.member.repository.MemberRepository;
import com.keyduck.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{	
	private final Path fileLocation;
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public MemberService(FileUploadProperties prop,BCryptPasswordEncoder passwordEncoder,MemberRepository memberRepository) {
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
				.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}
	
	
	public MemberCreateDto signup(MemberCreateDto newmember) {
		String rawPassword = newmember.getPassword();
		String encodedPassword = passwordEncoder.encode((CharSequence)rawPassword);
		newmember.setPassword(encodedPassword);
		memberRepository.save(newmember.toEntity());
		
		return newmember;
		
	}
	
	public String signin(MemberLoginDto loginmember,JwtTokenProvider jwtTokenProvider) {
		//null 예외처리 해줄 것.
		System.out.println(memberRepository.findByEmail(loginmember.getEmail()));
		Optional<Member> member = memberRepository.findByEmail(loginmember.getEmail());
		//if(! passwordEncoder.matches(loginmember.getPassword(),member.getPassword()){
			//비번 불일치 예외처리 해줄 것.
		//}
		return jwtTokenProvider.createToken(member.get().getUsername(), member.get().getRole());
	}


	public Member uploadFile(Long mem_id, MultipartFile req) throws IOException {
		Member member  = memberRepository.findById(mem_id).orElse(null);
		String filename = StringUtils.cleanPath(req.getOriginalFilename());
		Path targetLocation = this.fileLocation.resolve(filename);
		System.out.println();
		Files.copy(req.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		
		String profile_url = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/uploads/")
				.path(filename)
				.toUriString();
		
		member.setProfile(profile_url);
		memberRepository.save(member);
		return member;
	}

	public File downloadFile(String fileName) throws IOException {
		try {
			Path filePath = this.fileLocation.resolve(fileName).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());
            return resource.getFile();
		}catch(MalformedURLException e) {
			throw new FileDownloadException(fileName, e);
		}
	}
				
}