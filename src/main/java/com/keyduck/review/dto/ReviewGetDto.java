package com.keyduck.review.dto;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberGetDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ReviewGetDto {
    private SimpleKeyboardDto keyboard;
    private Float star;
    private MemberGetDto member;
    private String content;
}
