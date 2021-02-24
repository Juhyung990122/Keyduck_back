package com.keyduck.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberCreateDto {
	
	private String nickname;
	private String email;
	private String role;
	
	@Builder
	public void builder(String nickname, String email, String role) {
		this.nickname = nickname;
		this.email = email;
		this.role = role;
	}
	
}
