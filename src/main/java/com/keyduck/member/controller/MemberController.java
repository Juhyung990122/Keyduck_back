package com.keyduck.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.service.MemberService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class MemberController {
	private MemberService memberService;
	
	@Autowired
	public void Service(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@PostMapping("/registration")
	public ResponseEntity<?> signup(@RequestBody MemberCreateDto m) {
		MemberCreateDto newMember = memberService.signup(m);
		return new ResponseEntity<MemberCreateDto>(newMember, HttpStatus.OK);
	}
}

