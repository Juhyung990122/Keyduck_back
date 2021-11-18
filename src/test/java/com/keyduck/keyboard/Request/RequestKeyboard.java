package com.keyduck.keyboard.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestKeyboard {
    private String name;
    private String brand;
    private String connect;
    private String hotswap;
    private Integer price;
    private String led;
    private Integer arrangement;
    private Integer weight;
    private String cable;

    private String switchBrand;
    private String switchColor;
    private String thumbnailImg;
    private String descriptionImg;

    private String keycap;
    private String keycapImprint;
    private String keycapProfile;
    private String keyword;
}
