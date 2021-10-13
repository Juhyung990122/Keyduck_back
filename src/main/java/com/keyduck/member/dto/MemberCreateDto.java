package com.keyduck.member.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.keyduck.member.domain.Member;
import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateDto {

	@NotEmpty
	private String email;
	private String nickname = null;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(nullable = true)
	private String password = null;
	@NotNull
	private MemberRole role = MemberRole.USER;
	@NotNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private MemberType type = MemberType.Keyduck;

	
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
