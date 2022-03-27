package com.keyduck.keyboard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    private String content;

    public Tag(String content){
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(tagId, tag.tagId) && Objects.equals(content, tag.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, content);
    }
}

