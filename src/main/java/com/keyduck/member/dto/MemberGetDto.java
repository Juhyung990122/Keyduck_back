package com.keyduck.member.dto;

import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberGetDto {
	private Long memId;
	private String email;
	private String nickname;
	private MemberRole role;
}
