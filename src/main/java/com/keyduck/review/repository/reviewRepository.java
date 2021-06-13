package com.keyduck.review.repository;

import com.keyduck.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface reviewRepository extends JpaRepository<Review,Long> {
}
