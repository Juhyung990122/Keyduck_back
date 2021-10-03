package com.keyduck.member.socialLogin.service;

import com.keyduck.member.socialLogin.SocialLoginType;

import java.io.IOException;

public interface SocialOauth {
    String requestAccessToken(String code) throws IOException;

    default SocialLoginType type(){
        if(this instanceof KakaoOauth){
            return SocialLoginType.KAKAO;
        } else{
            return null;
        }
    }
}
