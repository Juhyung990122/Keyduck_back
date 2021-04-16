package com.keyduck.keyboard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "keyboard")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Keyboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyId;
    //키보드
    private String model;
    private String brand;
    private Connection connect;
    private boolean hotswap;
    private Integer price;
    private Frame frame;
    private boolean led;
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

}
