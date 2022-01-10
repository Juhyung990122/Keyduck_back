package com.keyduck.keyboard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue
    private Long tagId;

    private String content;

    public Tag(String content){
        this.content = content;
    }
}

