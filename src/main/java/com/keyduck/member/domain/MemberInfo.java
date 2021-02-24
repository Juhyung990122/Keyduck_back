package com.keyduck.member.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Getter;


public class MemberInfo{
	
	String nickname;
	@Column(unique = true)
	String email;
	String password;

}
	
	
