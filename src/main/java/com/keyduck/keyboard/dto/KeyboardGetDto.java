package com.keyduck.keyboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KeyboardGetDto {
    private String model;
    private String brand;
    private String connect;
    private Integer hotswap;
    private Integer price;
    private String led;
    private Integer arrangement;
    private Integer weight;
    private String cable;
    //스위치
    private String switchBrand;
    private String switchColor;
    private String photo;
    //키캡
    private String keycap;
    private String keycapImprint;
    private String keycapProfile;
    private String keyword;
}
