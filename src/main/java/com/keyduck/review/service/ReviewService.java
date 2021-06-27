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


    public List<ReviewGetDto> getReviewsByModel(Long id) {
        Keyboard keyboard = keyboardRepository.getOne(id);
        List<Review> reviewList = reviewRepository.findAllByName(keyboard);
        List<ReviewGetDto> reviewListDto = new ArrayList<ReviewGetDto>();

        for(int i = 0; i < reviewList.size(); i++){
            Review review = reviewList.get(i);
            reviewListDto.add(reviewMapper.toDto(review));
        }
        return reviewListDto;
    }

    public List<ReviewGetDto> getReviewsByAuthor(Long id) {
        Member member = memberRepository.getOne(id);
        List<Review> reviewList = reviewRepository.findAllByAuthor(member);
        List<ReviewGetDto> reviewListDto = new ArrayList<ReviewGetDto>();

        for(int i = 0; i < reviewList.size(); i++){
            Review review = reviewList.get(i);
            reviewListDto.add(reviewMapper.toDto(review));
        }

        return reviewListDto;
    }

    public ReviewGetDto getReviewDetail(Long reviewId) throws NullPointerException{
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new NullPointerException("해당 리뷰를 찾을 수 없습니다."));
        ReviewGetDto reviewDto = reviewMapper.toDto(review);
        return reviewDto;
    }

    public ReviewCreateDto addReview(ReviewCreateDto reviewInfo){
        Keyboard keyboard = keyboardRepository.findById(reviewInfo.getName()).orElseThrow(()->new NullPointerException("해당 리뷰를 찾을 수 없습니다."));
        Member member = memberRepository.findById(reviewInfo.getAuthor()).orElseThrow(()->new NullPointerException("해당 리뷰를 찾을 수 없습니다."));

        Review review = reviewInfo.toEntity(keyboard,member);
        reviewRepository.save(review);
        return reviewInfo;
    }


}
