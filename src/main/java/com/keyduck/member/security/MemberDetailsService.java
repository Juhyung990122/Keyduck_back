package com.keyduck.member.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.keyduck.member.repository.MemberRepository;

@Service
public class MemberDetailsService implements UserDetailsService {
	
	private MemberRepository memberRepository;
	
	public void Repository(MemberRepository memberRepository) {
		this.memberRepository =memberRepository;
	}
		
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}

}
