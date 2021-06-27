package com.keyduck.review.dto;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ReviewGetDto {
    private Keyboard name;
    private Float star;
    private Member author;
    private String content;
}
