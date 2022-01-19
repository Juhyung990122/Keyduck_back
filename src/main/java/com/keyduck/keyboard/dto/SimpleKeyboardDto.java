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
    private Integer star;

    public SimpleKeyboardDto toDto(Keyboard keyboard) {
        SimpleKeyboardDto keyboardDto = new SimpleKeyboardDto();
        keyboardDto.keyboardId = keyboard.getKeyboardId();
        keyboardDto.thumbnailImg = keyboard.getThumbnailImg();
        keyboardDto.name = keyboard.getName();
        keyboardDto.price = keyboard.getPrice();
        keyboardDto.star = keyboard.getStar();
        return keyboardDto;
    }
}
