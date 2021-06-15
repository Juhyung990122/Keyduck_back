package com.keyduck.review.service;

import com.keyduck.mapper.ReviewMapper;
import com.keyduck.review.domain.Review;
import com.keyduck.review.dto.ReviewGetDto;
import com.keyduck.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    @Autowired
    private ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewGetDto> getModelReview(String model) {
        List<Review> reviewList = reviewRepository.findAllByModel(model);
        List<ReviewGetDto> reviewListDto = new ArrayList<ReviewGetDto>();

        for(int i = 0; i < reviewList.size(); i++){
            Review review = reviewList.get(i);
            reviewListDto.add(reviewMapper.toDto(review));
        }

        return reviewListDto;
    }


}
