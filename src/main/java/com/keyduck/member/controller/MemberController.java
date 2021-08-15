package com.keyduck.member.controller;

import com.keyduck.member.dto.MemberCreateDto;
import com.keyduck.member.dto.MemberDeleteDto;
import com.keyduck.member.dto.MemberGetDto;
import com.keyduck.member.dto.MemberLoginDto;
import com.keyduck.member.service.MemberService;
import com.keyduck.result.CommonResult;
import com.keyduck.result.ListResult;
import com.keyduck.result.ResponseService;
import com.keyduck.result.SingleResult;
import com.keyduck.security.JwtTokenProvider;
import io.swagger.annotations.*;
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
@Api(tags = "Member", value = "KeyduckController v1")
public class MemberController {
	
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private final ResponseService responseService;

	// 회원 CRUD 로직
	@ApiOperation(value = "회원 가입", notes = "신규 회원일 경우 가입을 진행합니다.")
	@PostMapping("/signup")
	public ResponseEntity<SingleResult<MemberCreateDto>> signup(@Valid @RequestBody MemberCreateDto m) {
		MemberCreateDto newMember = memberService.signup(m);
		return ResponseEntity
				.ok()
				.body(responseService.getSingleResult(newMember));
	}

	@ApiOperation(value = "로그인", notes = "회원이라면 로그인합니다.")
	@PostMapping("/signin")
	public ResponseEntity<SingleResult<MemberGetDto>> signin(@RequestBody MemberLoginDto m) throws Exception {
		MemberGetDto result = memberService.signin(m,jwtTokenProvider);
		return ResponseEntity
				.ok()
				.body(responseService.getSingleResult(result));
				
	}
	@ApiOperation(value = "전체 멤버 조회", notes = "전체 멤버를 조회합니다.")
	@GetMapping("/members")
	public ResponseEntity<ListResult<MemberGetDto>> getAllMembers(){
		List<MemberGetDto> result = memberService.getMembers();
		return ResponseEntity
				.ok()
				.body(responseService.getListResult(result));
	}
	@ApiOperation(value = "특정 멤버 조회", notes = "특정멤버를 조회합니다.")
	@GetMapping("/members/{memberId}")
	public ResponseEntity<SingleResult<MemberGetDto>> getMemberDetail(@PathVariable Long memberId) throws Exception {
		MemberGetDto result = memberService.getMemberDetail(Long.valueOf(memberId));
		return ResponseEntity
				.ok()
				.body(responseService.getSingleResult(result));
	}

	@ApiOperation(value = "프로필 사진 변경", notes = " 유저의 프로필 사진을 변경합니다.")
	@PatchMapping("members/{mem_id}/editProfile")
	public ResponseEntity<MemberGetDto> editProfile(@PathVariable("mem_id") Long mem_id,@RequestParam("profile") MultipartFile req) throws Exception {
		MemberGetDto result = memberService.uploadFile(mem_id,req);
		return ResponseEntity
				.ok()
				.body(result);
	}

	@ApiOperation(value = "멤버 탈퇴", notes = "멤버를 삭제합니다.")
	@DeleteMapping("members/leave")
	public ResponseEntity<CommonResult> leaveMember(@RequestBody MemberDeleteDto m) throws Exception {
		String result = memberService.getLeaveMember(m.getEmail());
		return ResponseEntity
				.ok()
				.body(responseService.getSuccessResult());
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
		return ResponseEntity
				.ok()
				.headers(headers)
				.body(resource);

				
	}
	
}

