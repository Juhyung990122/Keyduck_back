package com.keyduck.scrap.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.member.domain.Member;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(name = "scrap")
@IdClass(ScrapId.class)
public class Scrap implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;

    @Id
    @ManyToOne
    @JoinColumn(name="keyboard_id")
    private Keyboard keyboardId;

}
