package com.keyduck.member.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class LikesCreateDto {
    private String memberEmail;
    private List<String> keyboardModel;
}
