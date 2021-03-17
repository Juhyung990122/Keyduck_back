package com.keyduck.member.dto;

import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberGetDto {
	private String email;
	private String nickname;
	private MemberRole role;
	private MemberType type;
}
