package com.keyduck.socialLogin.service;

import com.keyduck.member.domain.Member;
import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;
import com.keyduck.member.dto.MemberTokenDto;
import com.keyduck.member.repository.MemberRepository;
import com.keyduck.security.JwtTokenProvider;
import com.keyduck.socialLogin.SocialLoginType;
import org.json.simple.parser.ParseException;

import java.util.Optional;

public interface SocialOauth {

    //String requestAccessToken(String code) throws IOException;
    MemberTokenDto socialSignin(String accessToken, String socialLoginType) throws ParseException;
    default SocialLoginType type(){
        if(this instanceof KakaoOauth){
            return SocialLoginType.KAKAO;
        } else{
            return null;
        }
    }


}
