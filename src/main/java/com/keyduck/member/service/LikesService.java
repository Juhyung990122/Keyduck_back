package com.keyduck.member.service;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.mapper.SimpleKeyboardMapper;
import com.keyduck.member.domain.Member;
import com.keyduck.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Service

public class LikesService {
    private final KeyboardRepository keyboardRepository;
    private final MemberRepository memberRepository;
    private final SimpleKeyboardMapper simpleKeyboardMapper;

    public LikesService(KeyboardRepository keyboardRepository, MemberRepository memberRepository, SimpleKeyboardMapper simpleKeyboardMapper) {
        this.keyboardRepository = keyboardRepository;
        this.memberRepository = memberRepository;
        this.simpleKeyboardMapper = simpleKeyboardMapper;
    }

    @Transactional
    public List<SimpleKeyboardDto> getLikes(Long memId) {
        Member member = memberRepository.getOne(memId);
        List<Keyboard> likeList = member.getLikes();
        List<SimpleKeyboardDto> result = new ArrayList<>();
        for(int i = 0; i < likeList.size(); i++){
            result.add(simpleKeyboardMapper.toDto(likeList.get(i)));
        }
        return result;
    }


    public String addLikes(Long memId,Long keyboardId) {
        Member member = memberRepository.findById(memId).orElseThrow(() -> new NullPointerException("해당 멤버가 없습니다."));
        List<Keyboard> likeList = member.getLikes();
        Keyboard keyboard = keyboardRepository.findById(keyboardId).orElseThrow(() -> new NullPointerException("해당 키보드가 없습니다."));
        likeList.add(keyboard);
        member.setLikes(likeList);
        memberRepository.save(member);
        return "좋아요 추가 성공했습니다.";
    }

    @Transactional
    public String deleteLikes(Long memId,Long keyboardId){
        Member member = memberRepository.findById(memId).orElseThrow(() -> new NullPointerException("해당 멤버가 없습니다."));
        List<Keyboard> likeList = member.getLikes();
        Keyboard keyboard = keyboardRepository.findById(keyboardId).orElseThrow(() -> new NullPointerException("해당 키보드가 없습니다."));
        likeList.remove(keyboard);
        memberRepository.save(member);
        return "좋아요 취소 성공했습니다.";
    }
}
