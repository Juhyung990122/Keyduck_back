package com.keyduck.member.socialLogin.controller;

import com.keyduck.member.socialLogin.SocialLoginType;
import com.keyduck.member.socialLogin.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
@Slf4j
public class OauthController {
    private final OauthService oauthService;

    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(@PathVariable SocialLoginType socialLoginType){
        log.info(String.valueOf(socialLoginType));
        oauthService.request(socialLoginType);
    }

    @GetMapping(value = "/kakao_login")
    public String callback(
            @PathVariable SocialLoginType socialLoginType,
            @RequestParam String code
    ){
        log.info(code);
        return oauthService.requestAccessToken(socialLoginType,code);
    }
}
