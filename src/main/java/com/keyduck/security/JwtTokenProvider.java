package com.keyduck.security;


import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.service.MemberService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;


@Component
public class JwtTokenProvider {
	@Value("spring.jwt.secret")
	private String secretKey;
	//유효시간 
	private long tokenValidMillisecond = 1000L * 60 * 60;
	private final MemberService memberService;
	

	public JwtTokenProvider(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@PostConstruct
	protected void init(){
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	//Claim 객체로 토큰 생성 = id값 + 역할값 + 날짜 로 세팅 후 비밀키 암호화해서 붙임!
	public String createToken(String mem_id ,MemberRole role) {
		Claims claims = Jwts.claims().setSubject(mem_id);
		claims.put("role",role.toString());
		Date now = new Date();
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime()+tokenValidMillisecond))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		System.out.println(memberService);
		UserDetails userDetails = memberService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private String getUserPk(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String resolveToken(HttpServletRequest req) {
		return req.getHeader("X-AUTH-TOKEN");
				
	}
	
	public boolean validateToken(String jwt) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
			return !claims.getBody().getExpiration().before(new Date());
		}catch(Exception e){
			return false;
		}
	}
	
}
