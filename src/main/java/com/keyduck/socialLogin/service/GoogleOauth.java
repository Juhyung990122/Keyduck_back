package com.keyduck.socialLogin.service;

import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberTokenDto;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

@Component
public class GoogleOauth implements SocialOauth{

    private OauthCheck oauthCheck;

    public GoogleOauth(OauthCheck oauthCheck) {
        this.oauthCheck = oauthCheck;
    }

    @Override
    public MemberTokenDto socialSignin(String accessToken, String socialLoginType) throws ParseException {
        return null;
    }

//    @Override
//    public MemberTokenDto socialSignin(String accessToken, String socialLoginType){
//
//        // 구글에 openID로 유저정보 가져오기(email,openId로)
//
//
//
//        Member member = oauthCheck.checkMemberExist(socialId, socialLoginType, email);
//        return oauthCheck.createToken(member);
//    }
}
