package com.keyduck.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
public class ReviewGetDto {
    private String model;
    private Float star;
    private String author;
    private String content;
}
