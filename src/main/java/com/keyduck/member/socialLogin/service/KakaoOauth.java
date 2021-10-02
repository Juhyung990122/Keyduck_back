package com.keyduck.member.socialLogin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth{
    @Value("${sns.kakao.client_id}")
    private String KAKAO_CLIENT_ID;
    @Value("${sns.kakao.redirect_uri}")
    private String KAKAO_REDIRECT_URI;
    @Value("${sns.kakao.response_type}")
    private String KAKAO_CODE;
    @Value("${sns.kakao.url}")
    private String KAKAO_URL;

    @Override
    public String getOauthRedirectUrl(){
        Map<String,Object> params = new HashMap<>();
        params.put("response_type",KAKAO_CODE);
        params.put("redirect_uri",KAKAO_REDIRECT_URI);
        params.put("client_id",KAKAO_CLIENT_ID);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));
        return KAKAO_URL + "?" + parameterString;

    }

    @Override
    public String requestAccessToken(String code) {
        //https://kauth.kakao.com/oauth/token?client_id=d5c1915fd9a1a45bc3a8fa76291045b8&redirect_uri=http://101.101.210.98:30000/v1/kakao_login&code=개인코드
        RestTemplate restTemplate = new RestTemplate();

        Map<String,Object> params=  new HashMap<>();
        params.put("code",code);
        params.put("client_id",KAKAO_CLIENT_ID);
        params.put("redirect_uri",KAKAO_REDIRECT_URI);

        ResponseEntity<String> responseEntity;
        responseEntity = restTemplate.postForEntity("https://kauth.kakao.com/oauth/token",params,String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return "구글 로그인 요청 처리 실패";
    }
}
