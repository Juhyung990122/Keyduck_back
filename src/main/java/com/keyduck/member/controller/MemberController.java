package com.keyduck.member.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.dto.MemberLoginDto;
import com.keyduck.member.dto.MemberUpdateDto;
import com.keyduck.member.repository.MemberRepository;
import com.keyduck.member.service.MemberService;
import com.keyduck.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

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
	@PatchMapping("{mem_id}/test")
	public ResponseEntity<Member> test(@PathVariable("mem_id") Long mem_id,@RequestParam("profile") MultipartFile req) throws IOException{
		byte[] reqToByte = req.getOriginalFilename().getBytes();
		Member member = memberService.test(mem_id, reqToByte);
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}
}

