package com.keyduck.member.service;


import java.util.Optional;

import javax.persistence.PrimaryKeyJoinColumn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.dto.MemberLoginDto;
import com.keyduck.member.repository.MemberRepository;
import com.keyduck.security.JwtTokenProvider;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	

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

	
				
}