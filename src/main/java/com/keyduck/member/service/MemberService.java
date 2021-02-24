package com.keyduck.member.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.repository.MemberRepository;


@Service
public class MemberService {
	
	private MemberRepository memberRepository;
	
	public void Repository(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	public MemberCreateDto signup(@RequestBody Member member) {
		
	}
	
}
