package com.keyduck.keyboard.dto;

import lombok.*;


@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Builder(builderMethodName = "KeyboardSearchDtoBuilder")
public class KeyboardSearchDto {
    private String searchKeyword;
}
