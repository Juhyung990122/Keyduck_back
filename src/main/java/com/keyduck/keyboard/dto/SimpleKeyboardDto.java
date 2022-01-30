package com.keyduck.keyboard.dto;

import com.keyduck.keyboard.domain.Keyboard;
import javassist.Loader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Key;

@Getter
@Setter
@NoArgsConstructor
public class SimpleKeyboardDto {
    private Long keyboardId;
    private String thumbnailImg;
    private String name;
    private Integer price;
    private Float star;

}
