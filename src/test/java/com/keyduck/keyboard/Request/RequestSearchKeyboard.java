package com.keyduck.keyboard.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestSearchKeyboard {
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
    private String[] tag;
}
