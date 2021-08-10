package com.keyduck.member.service;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.member.domain.Member;
import com.keyduck.member.repository.MemberRepository;
import com.keyduck.member.dto.LikesCreateInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service

public class LikesService {
    private final KeyboardRepository keyboardRepository;
    private final MemberRepository memberRepository;

    public LikesService(KeyboardRepository keyboardRepository, MemberRepository memberRepository) {
        this.keyboardRepository = keyboardRepository;
        this.memberRepository = memberRepository;
    }



}
