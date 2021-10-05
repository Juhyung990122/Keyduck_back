package com.keyduck.member.socialLogin.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.keyduck.member.dto.MemberGetDto;
import com.keyduck.member.socialLogin.SocialLoginType;
import com.keyduck.member.socialLogin.service.OauthService;
import com.keyduck.result.ListResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
@Slf4j
public class OauthController {
    private final OauthService oauthService;

    @GetMapping( value = "/{socialLoginType}/kakao_login",
    produces = "application/json; charset=utf-8"
    )
    public ResponseEntity<String> callback(
            @PathVariable SocialLoginType socialLoginType,
            @RequestParam String code
    ) throws IOException {
        String result = oauthService.requestAccessToken(socialLoginType, code);
        log.info(code);
        return ResponseEntity
                .ok()
                .body(result);
    }


}
