package com.keyduck.keyboard.domain;

import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Document(indexName = "keyboards")
@Entity
@Table(name = "keyboard")
@Builder(builderMethodName = "KeyboardBuilder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Keyboard {

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    @JoinColumn(name = "keyboardId")
    private Long keyboardId;
    //키보드
    private String thumbnailImg;
    private String descriptionImg;
    private String name;
    private Date date;
    private String brand;
    private String connect;
    //boolean
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
    //키캡
    private String keycap;
    private String keycapImprint;
    private String keycapProfile;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "keyboard")
    private List<KeyboardTags> tags;
    @Field(type = FieldType.Text)
    private String info;

    public SimpleKeyboardDto toDto() {
        SimpleKeyboardDto keyboardDto = new SimpleKeyboardDto();
        keyboardDto.setKeyboardId(keyboardId);
        keyboardDto.setThumbnailImg(thumbnailImg);
        keyboardDto.setName(name);
        keyboardDto.setPrice(price);
        keyboardDto.setStar(star);
        return keyboardDto;
    }
}

