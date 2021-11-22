package com.keyduck.socialLogin;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum SocialLoginType {
    GOOGLE("Google"),
    KAKAO("Kakao"),
    NAVER("Naver");

    private String type;
    SocialLoginType(String type) {
        this.type = type;
    }

    public static final Map<String, SocialLoginType> map = new HashMap<>();
    static { for (SocialLoginType os : SocialLoginType.values()) {
        map.put(os.getType(), os); }
    }

    public static SocialLoginType getAuthType(String type) {
        return map.get(type);
    }
}
