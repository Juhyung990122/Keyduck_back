package com.keyduck.review.dto;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.member.domain.Member;
import com.keyduck.review.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@NoArgsConstructor
@Getter
public class ReviewCreateDto {
    private Long name;
    private Float star;
    private Long author;
    private String content;

    public Review toEntity(Keyboard keyboard, Member member){
        return Review.ReviewBuilder()
                .name(keyboard)
                .author(member)
                .star(star)
                .content(content)
                .build();

    }
}
