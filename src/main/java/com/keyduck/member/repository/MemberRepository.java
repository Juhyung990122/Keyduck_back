package com.keyduck.member.repository;
import com.keyduck.member.domain.Member;
import com.keyduck.member.domain.MemberType;
import com.keyduck.socialLogin.SocialLoginType;
import org.elasticsearch.monitor.os.OsStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findByEmail(String email);
	Optional<Member> findBySocialIdAndType(String socialId, MemberType type);
	Optional<Member> findByRefreshToken(String refreshToken);
}
