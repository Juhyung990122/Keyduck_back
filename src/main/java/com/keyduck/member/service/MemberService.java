package com.keyduck.member.service;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.keyduck.exception.FileUploadException;
import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.dto.MemberLoginDto;
import com.keyduck.member.dto.MemberUpdateDto;
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


	public Member test(Long mem_id, byte[] req) {
		Member member  = memberRepository.findById(mem_id).orElse(null);
		member.setProfile(req);
		memberRepository.save(member);
		System.out.println("success");
		System.out.println(member.getProfile());
		return member;
	}

	
				
}