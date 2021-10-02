package com.keyduck.member.socialLogin.service;

import com.keyduck.member.socialLogin.SocialLoginType;

public interface SocialOauth {
    String getOauthRedirectUrl();
    String requestAccessToken(String code);

    default SocialLoginType type(){
        if(this instanceof KakaoOauth){
            return SocialLoginType.KAKAO;
        } else{
            return null;
        }
    }
}
