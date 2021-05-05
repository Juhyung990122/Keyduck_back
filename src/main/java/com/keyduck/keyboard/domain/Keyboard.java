package com.keyduck.keyboard.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "keyboard")
@Builder(builderMethodName = "KeyboardBuilder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Keyboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long keyId;
    //키보드
    @Column(unique = true)
    private String model;
    private String brand;
    private String connect;
    private boolean hotswap;
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
