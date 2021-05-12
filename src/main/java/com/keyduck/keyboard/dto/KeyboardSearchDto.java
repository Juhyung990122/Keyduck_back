package com.keyduck.keyboard.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KeyboardSearchDto {
    private String model;
    private String brand;
    private String connect;
    private boolean hotswap;
    private Integer price;
    private Integer arrangement;
    private String keycapProfile;
    private String switchBrand;
}
