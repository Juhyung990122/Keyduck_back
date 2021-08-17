package com.keyduck.member.service;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.mapper.GenericMapper;
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
    private final GenericMapper genericMapper;

    public LikesService(KeyboardRepository keyboardRepository, MemberRepository memberRepository, KeyboardMapper genericMapper) {
        this.keyboardRepository = keyboardRepository;
        this.memberRepository = memberRepository;
        this.genericMapper = genericMapper;
    }


    public List<SimpleKeyboardDto> getLikes(Long memId) {
        Member member = memberRepository.getOne(memId);
        List<Keyboard> likeList = member.getLikes();
        List<SimpleKeyboardDto> result = new ArrayList<>();
        for(int i = 0; i < likeList.size(); i++){
            result.add((SimpleKeyboardDto) genericMapper.toDto(likeList.get(i)));
            System.out.println(result.get(0));
        }
        return result;
    }
}
