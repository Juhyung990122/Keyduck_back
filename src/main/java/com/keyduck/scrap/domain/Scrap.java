package com.keyduck.scrap.domain;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.member.domain.Member;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@IdClass(ScrapId.class)
public class Scrap {

    @Id
    @ManyToOne
    @JoinColumn(name="memberId")
    private Member memberId;

    @Id
    @ManyToOne
    @JoinColumn(name="keyboardId")
    private Keyboard keyboardId;

}
