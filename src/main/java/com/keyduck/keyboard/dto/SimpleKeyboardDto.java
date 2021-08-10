package com.keyduck.keyboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleKeyboardDto {
    private Long id;
    private String thumbnailImg;
    private String name;
    private Integer price;
    private Integer star;
}
