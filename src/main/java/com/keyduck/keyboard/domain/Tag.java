package com.keyduck.keyboard.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Tag{
    @Id
    private Long tagId;
    @ManyToOne(targetEntity = Keyboard.class)
    @JoinColumn(name = "keyboardId")
    private String tag;

}
