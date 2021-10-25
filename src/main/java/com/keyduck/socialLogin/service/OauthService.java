package com.keyduck.socialLogin.service;

import com.keyduck.member.dto.MemberTokenDto;
import com.keyduck.socialLogin.SocialLoginType;
import com.keyduck.socialLogin.dto.SocialToken;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final List<SocialOauth> socialOauthList;


    public MemberTokenDto login(String accessToken,String socialLoginType) throws ParseException {
        SocialOauth socialOauth = this.findSocialOauthByType(SocialLoginType.valueOf(socialLoginType));
        MemberTokenDto socialToken = socialOauth.socialSignin(accessToken,socialLoginType);
        return socialToken;
    }

    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }

}
