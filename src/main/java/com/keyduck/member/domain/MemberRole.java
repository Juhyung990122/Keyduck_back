package com.keyduck.member.domain;

public enum MemberRole {
	ADMIN("ADMIN"),
	USER("USER");

	private String roleName;
	
	MemberRole(String roleName) {
		this.roleName = roleName;
	}
	
	public String getKey() {
		return name();
	}
	
	public String getRoleName() {
		return roleName;
	}
	
}
