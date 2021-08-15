package com.keyduck.member.service;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.mapper.KeyboardMapper;
import com.keyduck.member.domain.Member;
import com.keyduck.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class LikesService {
    private final KeyboardRepository keyboardRepository;
    private final MemberRepository memberRepository;
    private final KeyboardMapper keyboardMapper;

    public LikesService(KeyboardRepository keyboardRepository, MemberRepository memberRepository, KeyboardMapper keyboardMapper) {
        this.keyboardRepository = keyboardRepository;
        this.memberRepository = memberRepository;
        this.keyboardMapper = keyboardMapper;
    }


    public List<SimpleKeyboardDto> getLikes(Long memId) {
        Member member = memberRepository.getOne(memId);
        List<Keyboard> likeList = member.getLikes();
        List<SimpleKeyboardDto> result = new ArrayList<>();
        for(int i = 0; i < likeList.size(); i++){
            //result.add(keyboardMapper.toDto(likeList.get(i)));
        }
        return result;
    }
}
