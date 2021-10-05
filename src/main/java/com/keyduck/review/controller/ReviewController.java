package com.keyduck.review.controller;

import com.keyduck.result.ListResult;
import com.keyduck.result.ResponseService;
import com.keyduck.result.SingleResult;
import com.keyduck.review.dto.ReviewCreateDto;
import com.keyduck.review.dto.ReviewGetDto;
import com.keyduck.review.repository.ReviewRepository;
import com.keyduck.review.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping(value = "/v1")
public class ReviewController {
    private final ReviewService reviewService;
    private final ResponseService responseService;

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository, ResponseService responseService) {
        this.reviewService = reviewService;
        this.responseService = responseService;
    }

    @ApiOperation(value = "모델별,멤버별 리뷰 조회", notes = "모델별,멤버별로 달린 리뷰를 조회합니다.")
    @GetMapping("/review")
    public ResponseEntity<ListResult<ReviewGetDto>> getReviews(@RequestParam String key, @RequestParam Long id) {
        List<ReviewGetDto> result = reviewService.getReviews(key,id);
        return ResponseEntity
                .ok()
                .body(responseService.getListResult(result));
    }


    //후기 디테일
    @ApiOperation(value = "리뷰 디테일 조회", notes = "선택한 리뷰의 상세정보를 조회합니다.")
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<SingleResult<ReviewGetDto>> getReviewDetail(@PathVariable Long reviewId){
        ReviewGetDto result = reviewService.getReviewDetail(reviewId);
        return ResponseEntity
                .ok()
                .body(responseService.getSingleResult(result));
    }


    // 후기 작성(회원)
    @ApiOperation(value = "리뷰 작성", notes = "리뷰를 작성합니다.")
    @PostMapping("/review/add")
    public ResponseEntity<SingleResult<ReviewCreateDto>> addReviewDetail(@RequestBody ReviewCreateDto reviewInfo){
        ReviewCreateDto result = reviewService.addReview(reviewInfo);
        return ResponseEntity
                .ok()
                .body(responseService.getSingleResult(result));
    }

    @ApiOperation(value = "리뷰 삭제", notes = "리뷰를 삭제합니다.")
    @DeleteMapping("/review/delete/{reviewId}")
    public ResponseEntity<SingleResult<String>> deleteReviewDetail(@PathVariable Long reviewId){
        String result = reviewService.deleteReview(reviewId);
        return ResponseEntity
                .ok()
                .body(responseService.getSingleResult(result));
    }




}
