package com.keyduck.member.controller;

import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.dto.MemberDeleteDto;
import com.keyduck.member.dto.MemberGetDto;
import com.keyduck.member.dto.MemberLoginDto;
import com.keyduck.member.service.MemberService;
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

	// 회원 CRUD 로직
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody MemberCreateDto m) {
		MemberCreateDto newMember = memberService.signup(m);
		if(newMember == null){
			return new ResponseEntity<String>("이미 가입된 회원입니다.",HttpStatus.CONFLICT);
		}
		return new ResponseEntity<MemberCreateDto>(newMember, HttpStatus.OK);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody MemberLoginDto m) throws Exception {
		String token = memberService.signin(m,jwtTokenProvider);
		if(token == null){
			return new ResponseEntity<String>("해당 멤버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(token, HttpStatus.OK);
				
	}

	@GetMapping("/members")
	public ResponseEntity<?> getAllMembers(){
		List<MemberGetDto> result = memberService.getMembers();
		return new ResponseEntity<List<MemberGetDto>>(result,HttpStatus.OK);
	}

	@GetMapping("/members/{mem_id}")
	public ResponseEntity<?> getMemberDetail(@PathVariable Long mem_id){
		System.out.println(mem_id);
		MemberGetDto result = memberService.getMemberDetail(mem_id);
		if(result == null){
			return new ResponseEntity<String>("해당 멤버를 찾을 수 없습니다.",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MemberGetDto>(result,HttpStatus.OK);
	}

	@PatchMapping("members/{mem_id}/editProfile")
	public ResponseEntity<?> editProfile(@PathVariable("mem_id") Long mem_id,@RequestParam("profile") MultipartFile req) throws IOException{
		Member result = memberService.uploadFile(mem_id,req);
		return new ResponseEntity<Member>(result, HttpStatus.OK);
	}

	@DeleteMapping("members/leave")
	public ResponseEntity<?> leaveMember(@RequestBody MemberDeleteDto m){
		String result = memberService.getLeaveMember(m.getMem_id());
		if(result == null){
			return new ResponseEntity<String>("해당 멤버를 찾을 수 없습니다.",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
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

