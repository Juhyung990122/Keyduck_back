package com.keyduck.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.repository.MemberRepository;

@Service
public class MemberService{
	
	private MemberRepository memberRepository;
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public void Repository(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	@Autowired
	public void PasswordEncode(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	
	public MemberCreateDto signup(MemberCreateDto newmember) {
		String rawPassword = newmember.getPassword();
		String encodedPassword = passwordEncoder.encode((CharSequence)rawPassword);
		newmember.setPassword(encodedPassword);
		memberRepository.save(newmember.toEntity());
		return newmember;
		
	}
	
				
}