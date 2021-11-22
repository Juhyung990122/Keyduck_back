package com.keyduck.socialLogin.controller;

import com.keyduck.member.dto.MemberTokenDto;
import com.keyduck.result.ResponseService;
import com.keyduck.result.SingleResult;
import com.keyduck.socialLogin.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController

@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
@Slf4j
public class OauthController {
    private final OauthService oauthService;
    private final ResponseService responseService;

//    @GetMapping( value = "/login/{socialLoginType}",
//    produces = "application/json; charset=utf-8"
//    )
//    public ResponseEntity<String> callback(
//            @PathVariable SocialLoginType socialLoginType,
//            @RequestParam String socialId
//    ) throws IOException {
//        String result = oauthService.requestAccessToken(socialLoginType, socialId);
//        log.info(socialId);
//        return ResponseEntity
//                .ok()
//                .body(result);
//    }

    @PostMapping( value = "/login/{socialLoginType}")
    public ResponseEntity<SingleResult<MemberTokenDto>> signin(
            @RequestHeader String accessToken,
            @PathVariable String socialLoginType) throws ParseException {
        MemberTokenDto result = oauthService.login(accessToken,socialLoginType);
        return ResponseEntity
                .ok()
                .body(responseService.getSingleResult(result));
    }


}
