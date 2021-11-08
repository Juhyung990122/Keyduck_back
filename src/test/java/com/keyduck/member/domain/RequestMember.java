package com.keyduck.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RequestMember {
    private String email;
    private String nickname = null;
    private String password = null;
    private MemberRole role = MemberRole.USER;
    private MemberType type = MemberType.Keyduck;
}
