package com.keyduck.review.controller;

import com.keyduck.result.ResponseService;
import com.keyduck.review.dto.ReviewCreateDto;
import com.keyduck.review.dto.ReviewGetDto;
import com.keyduck.review.repository.ReviewRepository;
import com.keyduck.review.service.ReviewService;
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
        String key = request.keySet().iterator().next();
        switch (key) {
            case "keyboardId":
                Long keyboardId = request.get("keyboardId");
                List<ReviewGetDto> modelResult = reviewService.getReviewsByModel(keyboardId);
                return new ResponseEntity<>(responseService.getListResult(modelResult), HttpStatus.OK);

            case "memberId":
                Long memberId = request.get("memberId");
                List<ReviewGetDto> memberResult = reviewService.getReviewsByAuthor(memberId);
                return new ResponseEntity<>(responseService.getListResult(memberResult), HttpStatus.OK);

            default:
                System.out.println("실패");
        }
        return new ResponseEntity<>("실패임", HttpStatus.BAD_REQUEST);
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




}
