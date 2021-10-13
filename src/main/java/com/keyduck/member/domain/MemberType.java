package com.keyduck.member.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum MemberType {
	Keyduck("keyduck"),
	Google("Google"),
	Kakao("Kakao");

	private String type;
	MemberType(String type) {
		this.type = type;
	}

	public static final Map<String,MemberType> map = new HashMap<>();
	static { for (MemberType os : MemberType.values()) {
		map.put(os.getType(), os); }
	}

	public static MemberType getAuthType(String type) {
		return map.get(type);
	}

}
