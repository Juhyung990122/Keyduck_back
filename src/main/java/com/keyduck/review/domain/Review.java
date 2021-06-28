package com.keyduck.review.domain;


import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "ReviewBuilder")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(targetEntity = Keyboard.class)
    private Keyboard name;
    private Float star;
    @ManyToOne(targetEntity = Member.class)
    private Member author;
    private String content;

}
