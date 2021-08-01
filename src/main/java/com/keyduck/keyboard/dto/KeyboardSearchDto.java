package com.keyduck.keyboard.dto;

import lombok.*;


@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Builder(builderMethodName = "KeyboardSearchDtoBuilder")
public class KeyboardSearchDto {
    private String name;
    private String brand;
    private String connect;
    private String hotswap;
    private int startPrice;
    private int endPrice;
    private int arrangement;
    private String keycapProfile;
    private String switchBrand;
    private String[] switchColor;

}
