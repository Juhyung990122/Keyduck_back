package com.keyduck.review.repository;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.member.domain.Member;
import com.keyduck.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByKeyboard(Keyboard id);
    List<Review> findAllByMember(Member id);
    Review findReviewByMemberAndKeyboard(Keyboard name, Member author);
}
