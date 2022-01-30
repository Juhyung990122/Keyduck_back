package com.keyduck.keyboard.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KeyboardGetDto {
    private Long keyboardId;
    private String name;
    private String brand;
    private String connect;
    private String hotswap;
    private Integer price;
    private String led;
    private Integer arrangement;
    private Integer weight;
    private String cable;
    private Float star;
    //스위치
    private String switchBrand;
    private String switchColor;
    private String thumbnailImg;
    private String descriptionImg;
    //키캡
    private String keycap;
    private String keycapImprint;
    private String keycapProfile;
    private String keyword;
}
