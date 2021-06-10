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

    public String saveLikes(LikesCreateInfo likeInfo){
        Member member = memberRepository.findByEmail(likeInfo.getMemberEmail())
                .orElseThrow(()-> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));

        List<String> likeModelList = likeInfo.getKeyboardModel();
        List<Keyboard> newLikeList = new ArrayList<Keyboard>();

        for(int i = 0; i < likeModelList.size(); i++){
            String model = likeModelList.get(i);
            newLikeList.add(keyboardRepository.findByModel(model)
                    .orElseThrow(()->new NoSuchElementException("해당 키보드를 찾을 수 없습니다.")));
        }
        member.setLikes(newLikeList);
        memberRepository.save(member);
        return "갱신 완료 되었습니다.";
    }

}
