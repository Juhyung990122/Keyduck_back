package com.keyduck.keyboard.dto;

import com.keyduck.keyboard.domain.Keyboard;

import com.keyduck.keyboard.domain.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class KeyboardCreateDto {
    private String name;
    private String brand;
    private String date;
    private String connect;
    private String hotswap;
    private Integer price;
    private String led;
    private Integer arrangement;
    private Integer weight;
    private String cable;
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

    public Keyboard toEntity() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월");

        return Keyboard.KeyboardBuilder()
                .name(name)
                .brand(brand)
                .date(format.parse(date))
                .connect(connect)
                .hotswap(hotswap)
                .price(price)
                .led(led)
                .arrangement(arrangement)
                .weight(weight)
                .cable(cable)
                .switchBrand(switchBrand)
                .switchColor(switchColor)
                .thumbnailImg(thumbnailImg)
                .descriptionImg(descriptionImg)
                .keycap(keycap)
                .keycapImprint(keycapImprint)
                .keycapProfile(keycapProfile)
                .build();
    }

}
