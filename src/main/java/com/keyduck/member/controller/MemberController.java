package com.keyduck.member.controller;

import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.dto.MemberDeleteDto;
import com.keyduck.member.dto.MemberGetDto;
import com.keyduck.member.dto.MemberLoginDto;
import com.keyduck.member.service.MemberService;
import com.keyduck.result.ResponseService;
import com.keyduck.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private final ResponseService responseService;

	// 회원 CRUD 로직
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody MemberCreateDto m) {
		MemberCreateDto newMember = memberService.signup(m);
		return new ResponseEntity<>(responseService.getSingleResult(newMember),HttpStatus.OK);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody MemberLoginDto m) throws Exception {
		String token = memberService.signin(m,jwtTokenProvider);
		return new ResponseEntity<>(responseService.getSingleResult(token), HttpStatus.OK);
				
	}

	@GetMapping("/members")
	public ResponseEntity<?> getAllMembers(){
		List<MemberGetDto> result = memberService.getMembers();
		return new ResponseEntity<>(responseService.getListResult(result),HttpStatus.OK);
	}

	@GetMapping("/members/{mem_id}")
	public ResponseEntity<?> getMemberDetail(@PathVariable Long mem_id) throws Exception {
		return new ResponseEntity<>(responseService.getSingleResult(memberService.getMemberDetail(mem_id)),HttpStatus.OK);
	}

	@PatchMapping("members/{mem_id}/editProfile")
	public ResponseEntity<?> editProfile(@PathVariable("mem_id") Long mem_id,@RequestParam("profile") MultipartFile req) throws Exception {
		MemberGetDto result = memberService.uploadFile(mem_id,req);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 지금은 JSON으로 mem_id에 아이디값 담아서 보내는 형식 - 링크에 포함시키는게 더 편한지 물어볼 것.
	@DeleteMapping("members/leave")
	public ResponseEntity<?> leaveMember(@RequestBody MemberDeleteDto m) throws Exception {
		String result = memberService.getLeaveMember(m.getEmail());
		return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
	}

	// 이미지 처리 관련 로직
	@GetMapping("/uploads/{fileName}")
	public ResponseEntity<Resource> downloadProfile(@PathVariable String fileName, HttpServletRequest request) throws IOException{
		Resource resource = memberService.downloadFile(fileName,request);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		}catch(IOException ex) {
			logger.info("타입을 정의할 수 없습니다.");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(contentType));
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
				
	}
	
}

