package com.keyduck.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.keyduck.member.domain.Member;
import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberCreateDto {
	
	private String nickname;
	private String email;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private MemberRole role;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private MemberType type;
	
	public Member toEntity() {
		return Member.MemberBuilder()
			.nickname(nickname)
			.email(email)
			.password(password)
			.role(role)
			.type(type)
			.build();
	}
	
}
