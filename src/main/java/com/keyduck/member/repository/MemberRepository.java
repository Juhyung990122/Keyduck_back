package com.keyduck.member.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.keyduck.member.domain.Member;

import antlr.collections.List;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findByEmail(String email);
}
