package com.keyduck.keyboard.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn
    private Long keyboardId;
    //키보드
    private String thumbnailImg;
    private String descriptionImg;
    private String name;
    private String brand;
    private String connect;
    //boolean
    private String hotswap;
    private Integer price;
    private String led;
    private Integer arrangement;
    private Integer weight;
    private String cable;
    //스위치
    private String switchBrand;
    private String switchColor;
    //키캡
    private String keycap;
    private String keycapImprint;
    private String keycapProfile;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tag")
    private List<Tag> tag;
}
