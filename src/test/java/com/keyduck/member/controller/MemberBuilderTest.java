package com.keyduck.member.controller;

import org.junit.jupiter.api.Test;
import com.keyduck.member.domain.Member;
import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;

class MemberBuilderTest {

	@Test
	void MemberBuilder() {
		Member member = Member.MemberBuilder()
				.nickname("test")
				.email("teset@naver.com")
				.password("1212")
				.role(MemberRole.ADMIN)
				.type(MemberType.Keyduck)
				.build();
		
		System.out.println(member.toString());
	}

}
