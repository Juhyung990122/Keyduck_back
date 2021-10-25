package com.keyduck.socialLogin.service;

import com.keyduck.member.dto.MemberLoginDto;
import com.keyduck.member.dto.MemberTokenDto;
import com.keyduck.socialLogin.SocialLoginType;
import com.keyduck.socialLogin.dto.SocialToken;
import org.json.simple.parser.ParseException;

public interface SocialOauth {
    //String requestAccessToken(String code) throws IOException;
    MemberTokenDto socialSignin(String accessToken, String socialLoginType) throws ParseException;
    default SocialLoginType type(){
        if(this instanceof KakaoOauth){
            return SocialLoginType.Kakao;
        } else{
            return null;
        }
    }
}
