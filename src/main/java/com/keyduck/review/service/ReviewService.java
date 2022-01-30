package com.keyduck.review.service;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.mapper.ReviewMapper;
import com.keyduck.member.domain.Member;
import com.keyduck.member.repository.MemberRepository;
import com.keyduck.review.domain.Review;
import com.keyduck.review.dto.ReviewCreateDto;
import com.keyduck.review.dto.ReviewGetDto;
import com.keyduck.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final KeyboardRepository keyboardRepository;
    private final MemberRepository memberRepository;
    @Autowired
    private ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, KeyboardRepository keyboardRepository, MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.keyboardRepository = keyboardRepository;
        this.memberRepository = memberRepository;
    }



//    public List<ReviewGetDto> getReviewsByAuthor(Long id) {
//        Member member = memberRepository.getOne(id);
//        List<Review> reviewList = reviewRepository.findAllByMember(member);
//        List<ReviewGetDto> reviewListDto = new ArrayList<ReviewGetDto>();
//
//        for(int i = 0; i < reviewList.size(); i++){
//            Review review = reviewList.get(i);
//            reviewListDto.add(reviewMapper.toDto(review));
//        }
//
//        return reviewListDto;
//    }

    public ReviewGetDto getReviewDetail(Long reviewId) throws NullPointerException{
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new NullPointerException("해당 리뷰를 찾을 수 없습니다."));
        ReviewGetDto reviewDto = reviewMapper.toDto(review);
        return reviewDto;
    }

    public ReviewCreateDto addReview(ReviewCreateDto reviewInfo, String accessToken){
        Keyboard keyboard = keyboardRepository.findById(reviewInfo.getKeyboardId()).orElseThrow(()->new NullPointerException("해당 리뷰를 찾을 수 없습니다."));
        Member member = memberRepository.findByAccessToken(accessToken).orElseThrow(()->new NullPointerException("해당 유저를 찾을 수 없습니다."));

        Review review = reviewInfo.toEntity(keyboard,member);
        reviewRepository.save(review);
        return reviewInfo;
    }


    public List<ReviewGetDto> getReviews(String key, Long id ) {
        switch (key) {
            case "keyboardId":
                Long keyboardId = id;
                Keyboard keyboard = keyboardRepository.getOne(keyboardId);
                List<Review> modelReviews = reviewRepository.findAllByKeyboard(keyboard);
                List<ReviewGetDto> keyboardReviewsDto = new ArrayList<ReviewGetDto>();

                for (int i = 0; i < modelReviews.size(); i++) {
                    Review review = modelReviews.get(i);
                    keyboardReviewsDto.add(reviewMapper.toDto(review));
                }
                return keyboardReviewsDto;

            case "memId":
                Long memberId = id;
                Member member = memberRepository.getOne(memberId);
                List<Review> memberReviews = reviewRepository.findAllByMember(member);
                List<ReviewGetDto> memberReviewsDto = new ArrayList<ReviewGetDto>();

                for (int i = 0; i < memberReviews.size(); i++) {
                    Review review = memberReviews.get(i);
                    memberReviewsDto.add(reviewMapper.toDto(review));
                }

                return memberReviewsDto;

            default:
                //예외처리할 것
                throw new RuntimeException("실패");
        }
    }

    public String deleteReview(Long reviewId) {
        Review deleteReview = reviewRepository.getOne(reviewId);
        reviewRepository.delete(deleteReview);
        return "성공적으로 삭제했습니다.";
    }
}