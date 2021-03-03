package com.keyduck.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.dto.MemberLoginDto;
import com.keyduck.member.service.MemberService;
import com.keyduck.security.JwtTokenProvider;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;
	

	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody MemberCreateDto m) {
		MemberCreateDto newMember = memberService.signup(m);
		return new ResponseEntity<MemberCreateDto>(newMember, HttpStatus.OK);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody MemberLoginDto m) {
		String token = memberService.signin(m,jwtTokenProvider);
		return new ResponseEntity<String>(token, HttpStatus.OK);
				
	}
}

