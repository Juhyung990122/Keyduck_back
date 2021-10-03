package com.keyduck.member.socialLogin.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth {
    @Value("${sns.kakao.client_id}")
    private String KAKAO_CLIENT_ID;
    @Value("${sns.kakao.redirect_uri}")
    private String KAKAO_REDIRECT_URI;
    @Value("${sns.kakao.client_secret}")
    private String KAKAO_SECRET;

    @Override
    public String requestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        final MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("redirect_uri", KAKAO_REDIRECT_URI);
        params.add("code", code);
        params.add("client_secret",KAKAO_SECRET);

        HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(params, headers);


        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("https://kauth.kakao.com/oauth/token",request,String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity.getBody();
        }
        return "로그인 요청을 실패했습니다";
    }
}

