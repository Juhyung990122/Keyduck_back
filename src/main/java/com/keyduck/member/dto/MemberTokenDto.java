package com.keyduck.member.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "MemberTokenBuilder")
public class MemberTokenDto {
    private Long memId;
    private String access_token;
    private String refresh_token;
}
