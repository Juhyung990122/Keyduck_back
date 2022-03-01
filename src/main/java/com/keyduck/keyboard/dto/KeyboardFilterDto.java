package com.keyduck.keyboard.dto;

import lombok.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Builder(builderMethodName = "KeyboardFilterDtoBuilder")
public class KeyboardFilterDto {
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
    private String led;
    private int weight;
}
