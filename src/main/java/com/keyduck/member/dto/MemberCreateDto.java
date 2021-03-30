package com.keyduck.member.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.keyduck.member.domain.Member;
import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class MemberCreateDto {

	@NotEmpty
	private String email;
	private String nickname;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotEmpty
	private String password;
	@NotNull
	private MemberRole role;
	@NotNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private MemberType type;

	
	public void setPassword(String encodedPassword) {
		this.password =encodedPassword;
		
	}

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
