package com.keyduck.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ReviewGetDto {
    private String model;
    private Float star;
    private String author;
    private String content;
}
