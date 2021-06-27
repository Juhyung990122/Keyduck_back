package com.keyduck.keyboard.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
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
}
