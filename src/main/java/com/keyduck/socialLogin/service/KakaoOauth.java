package com.keyduck.socialLogin.service;


import com.keyduck.member.domain.Member;
import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;
import com.keyduck.member.dto.MemberTokenDto;
import com.keyduck.member.repository.MemberRepository;
import com.keyduck.security.JwtTokenProvider;

import com.keyduck.socialLogin.dto.SocialToken;
import org.apache.tomcat.util.net.openssl.ciphers.Encryption;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public MemberTokenDto socialSignin(String accessToken, String socialLoginType) throws ParseException {
        String socialId = null;
        String email = null;

        //access토큰으로 사용자 가져오기 -> socialid get
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
            System.out.println(convertedUserInfo);
            socialId = String.valueOf(convertedUserInfo.get("id"));
            email = String.valueOf(((JSONObject)convertedUserInfo.get("kakao_account")).get("email"));

            System.out.println(email);
        }

        if(socialId == null){
            throw new NoSuchElementException("사용자가 존재하지 않습니다");
        }

        //사용자 가져와서 findByTypeandSocialId -> 기존 로그인 여부 확인
        Optional<Member> findMember= memberRepository.findBySocialIdAndType(socialId,MemberType.getAuthType(socialLoginType));
        Member member;
        //있으면 사용자 정보 get
        //없으면 필요한 정보 저장(socialId,type,email)
        if(findMember.isPresent()){
            member = findMember.get();
        }else{
            member = Member.MemberBuilder()
                    .socialId(socialId)
                    .email(email)
                    .type(MemberType.getAuthType(socialLoginType))
                    .role(MemberRole.USER)
                    .build();
            memberRepository.save(member);
        }

        //이메일 검색해서 확인 없으면 이메일도 같이 넣어줌
        //token 생성
        String keyduckAccessToken = jwtTokenProvider.createToken(member.getUsername(), member.getRole());
        String keyduckRefreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(),member.getRole());
        member.setAccessToken(keyduckAccessToken);
        member.setRefreshToken(keyduckRefreshToken);
        memberRepository.save(member);
        //kakaoToken에 mem_id랑 토큰들 넣어서 반환.
        return MemberTokenDto.MemberTokenBuilder()
                .memId(member.getMemId())
                .access_token(keyduckAccessToken)
                .refresh_token(keyduckRefreshToken)
                .build();
    }
}

