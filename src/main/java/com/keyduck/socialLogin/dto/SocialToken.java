package com.keyduck.socialLogin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderMethodName = "SocialTokenBuilder")
public class SocialToken {
    private Long memId;
    private String access_token;
    private String refresh_token;
}
