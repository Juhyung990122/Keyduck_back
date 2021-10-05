package com.keyduck.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginMemberDto {
    private Long memId;
    private String token;
}
