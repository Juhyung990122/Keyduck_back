package com.keyduck.socialLogin.service;

import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberTokenDto;
import com.keyduck.utils.JsonConverter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Component
public class GoogleOauth implements SocialOauth {

    private OauthCheck oauthCheck;

    public GoogleOauth(OauthCheck oauthCheck) {
        this.oauthCheck = oauthCheck;
    }


    @Override
    public MemberTokenDto socialSignin(String accessToken, String socialLoginType) throws ParseException {
        String socialId = null;
        String email = null;
        String nickname = null;

        // 구글에 openID로 유저정보 가져오기(email,openId)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange("https://www.googleapis.com/oauth2/v1/userinfo?alt=json", HttpMethod.GET,request,String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String userInfo = responseEntity.getBody();
            JSONObject convertedUserInfo = JsonConverter.convert(userInfo);
//            JSONParser parser = new JSONParser();
//            Object obj = parser.parse(userInfo);
//            JSONObject convertedUserInfo = (JSONObject) obj;
            socialId = String.valueOf(convertedUserInfo.get("id"));
            email = String.valueOf((convertedUserInfo.get("email")));
            nickname = String.valueOf(convertedUserInfo.get("name"));

        }

        if (socialId == null) {
            throw new NoSuchElementException("사용자가 존재하지 않습니다");
        }


        Member member = oauthCheck.checkMemberExist(socialId, socialLoginType, email,nickname);
        return oauthCheck.createToken(member);

    }
}
