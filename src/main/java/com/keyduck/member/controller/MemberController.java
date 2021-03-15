package com.keyduck.member.controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.dto.MemberLoginDto;

import com.keyduck.member.service.MemberService;
import com.keyduck.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;


import java.io.File;
import java.io.IOException;



import javax.servlet.http.HttpServletRequest;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	
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
	
	@PatchMapping("{mem_id}/editProfile")
	public ResponseEntity<?> editProfile(@PathVariable("mem_id") Long mem_id,@RequestParam("profile") MultipartFile req) throws IOException{
		Member member = memberService.uploadFile(mem_id,req);
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}
	
	@GetMapping("/uploads/{fileName}")
	public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) throws IOException{
		Resource resource = memberService.downloadFile(fileName,request);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		}catch(IOException ex) {
			logger.info("타입을 정의할 수 없습니다. ");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(contentType));
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
				
	}
}

