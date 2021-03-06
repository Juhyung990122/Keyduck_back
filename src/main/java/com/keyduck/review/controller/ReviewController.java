package com.keyduck.review.controller;

import com.keyduck.result.ResponseService;
import com.keyduck.review.domain.Review;
import com.keyduck.review.dto.ReviewCreateDto;
import com.keyduck.review.dto.ReviewGetDto;
import com.keyduck.review.repository.ReviewRepository;
import com.keyduck.review.service.ReviewService;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/v1")
public class ReviewController {
    private final ReviewService reviewService;
    private final ResponseService responseService;

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository, ResponseService responseService) {
        this.reviewService = reviewService;
        this.responseService = responseService;
    }

    @GetMapping("/review")
    public ResponseEntity<?> getModelReview(@RequestBody HashMap<String,Long> request) {
        List<ReviewGetDto> result = reviewService.getReviews(request);
        return new ResponseEntity<>(responseService.getListResult(result), HttpStatus.OK);
    }

    //후기 디테일
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<?> getReviewDetail(@PathVariable Long reviewId){
        ReviewGetDto result = reviewService.getReviewDetail(reviewId);
        return new ResponseEntity<>(responseService.getSingleResult(result), HttpStatus.OK);
    }

    // 후기 작성(회원)
    @PostMapping("/review/add")
    public ResponseEntity<?> addReviewDetail(@RequestBody ReviewCreateDto reviewInfo){
        ReviewCreateDto result = reviewService.addReview(reviewInfo);
        return new ResponseEntity<>(responseService.getSingleResult(result), HttpStatus.OK);
    }

    // 후기 삭제(관리자)
    @DeleteMapping("/review/delete/{reviewId}")
    public ResponseEntity<?> deleteReviewDetail(@PathVariable Long reviewId){
        String result = reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(responseService.getSingleResult(result),HttpStatus.OK);
    }




}
