package com.keyduck.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Setter
@Getter
@NoArgsConstructor
public class RequestMember {
    @NotEmpty
    private String email;
    @NotNull
    private String password;
}
