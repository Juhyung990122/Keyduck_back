package com.keyduck.member.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "member")
@Builder(builderMethodName = "MemberBuilder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long memberId;

	String nickname;
	@Column(unique = true)
	String email;
	String password;

	@Enumerated(EnumType.STRING)
	private MemberType type;
	
	@Enumerated(EnumType.STRING)
	private MemberRole role;
	
	private String profile;
	
	public void setProfile(String profile){
		this.profile = profile;
	}
	
	@Override
	public String getUsername() {
		return this.memberId.toString();
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


	