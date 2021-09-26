package com.keyduck.keyboard.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class MainImage {
    @Id
    private Long mainId;
    private String imageUrl;
}
