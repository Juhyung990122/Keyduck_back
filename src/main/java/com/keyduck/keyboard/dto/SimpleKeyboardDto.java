package com.keyduck.keyboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SimpleKeyboardDto {
    private Long keyboardId;
    private String thumbnailImg;
    private String name;
    private Integer price;
    private Integer star;
}
