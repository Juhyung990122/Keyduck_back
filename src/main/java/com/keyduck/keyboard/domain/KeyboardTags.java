package com.keyduck.keyboard.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity

public class KeyboardTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Keyboard keyboard;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    public KeyboardTags(Keyboard keyboard, Tag tag) {
        this.keyboard = keyboard;
        this.tag = tag;
    }
}
