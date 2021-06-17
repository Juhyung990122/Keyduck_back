package com.keyduck.review.controller;

import com.keyduck.mapper.ReviewMapper;
import com.keyduck.review.dto.ReviewGetDto;
import com.keyduck.review.repository.ReviewRepository;
import com.keyduck.review.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/v1")
public class ReviewController {
    private final ReviewService reviewService;


    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
    }

    // 모델별 후기
    @GetMapping("/review")
    public List<ReviewGetDto> getModelReview(@RequestBody String model){
        List<ReviewGetDto> result = reviewService.getModelReview(model);
        return result;
    }
    // 사용자별 후기

    // 후기 작성(회원)

    // 후기 삭제(관리자)



}
