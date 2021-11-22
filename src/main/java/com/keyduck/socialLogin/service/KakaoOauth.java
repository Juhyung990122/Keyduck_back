package com.keyduck.socialLogin.service;


import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberTokenDto;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth {

    private OauthCheck oauthCheck;
    public KakaoOauth(OauthCheck oauthCheck){
        this.oauthCheck = oauthCheck;
    }

    @Override
    public MemberTokenDto socialSignin(String accessToken, String socialLoginType) throws ParseException {
        String socialId = null;
        String email = null;

        // access토큰으로 사용자 가져오기 -> socialid get
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",accessToken);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(null,headers);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("https://kapi.kakao.com/v2/user/me",request,String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            String userInfo = responseEntity.getBody();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(userInfo);
            JSONObject convertedUserInfo = (JSONObject) obj;
            socialId = String.valueOf(convertedUserInfo.get("id"));
            email = String.valueOf(((JSONObject)convertedUserInfo.get("kakao_account")).get("email"));

        }

        if(socialId == null){
            throw new NoSuchElementException("사용자가 존재하지 않습니다");
        }

        Member member = oauthCheck.checkMemberExist(socialId,socialLoginType,email);
        return oauthCheck.createToken(member);
    }



}

