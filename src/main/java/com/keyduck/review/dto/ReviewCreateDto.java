package com.keyduck.review.dto;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.member.domain.Member;
import com.keyduck.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewCreateDto {
    private Long keyboardId;
    private Float star;
    private String content;

    public Review toEntity(Keyboard keyboard, Member member){
        return Review.ReviewBuilder()
                .keyboard(keyboard)
                .member(member)
                .star(star)
                .content(content)
                .build();

    }
}
