package com.keyduck.review.dto;

import com.keyduck.review.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@NoArgsConstructor
@Getter
public class ReviewCreateDto {
    private String model;
    private Float star;
    @Email
    private String author;
    private String content;

    public Review toEntity(){
        return Review.ReviewBuilder()
                .model(model)
                .star(star)
                .author(author)
                .content(content)
                .build();

    }
}
