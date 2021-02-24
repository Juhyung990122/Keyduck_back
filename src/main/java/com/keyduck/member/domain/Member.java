package com.keyduck.member.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;


@Entity
@Table(name = "member")
public class Member implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long mem_id;
	
	//비슷한 속성을 지닌 애들 혹은 여기저기서 쓸 애을 임베디드로 묶어 객체로 표현(응집성, 가독성 올라감 + 복합키로도 사용가능)
	//만약 테이블값이 중복된다면 attrbuteoverride로 속성재설정 
	@Embedded
	private MemberInfo memberInfo;
	
	@Enumerated(EnumType.STRING)
	private MemberType type;
	
	@Enumerated(EnumType.STRING)
	private MemberRole role;
	
	@Override
	public String getUsername() {
		return this.memberInfo.nickname;
	}
	
	@Override
	public String getPassword() {
		return this.memberInfo.password;
	}

	
	@Override
	 public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ROLE_" + role));
		return list;
	}
		
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


}


	