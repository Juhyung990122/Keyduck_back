package com.keyduck.keyboard.service;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardCreateDto;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.dto.KeyboardSearchDto;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.keyboard.repository.KeyboardSpecification;
import com.keyduck.mapper.KeyboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class KeyboardService {
    private final KeyboardRepository keyboardRepository;
    private final KeyboardMapper keyboardMapper;

    public KeyboardService(KeyboardRepository keyboardRepository, KeyboardMapper keyboardMapper){
        this.keyboardRepository = keyboardRepository;
        this.keyboardMapper = keyboardMapper;
    }

    public List<KeyboardGetDto> getAllKeyboards(){
        List<Keyboard> keyboards = keyboardRepository.findAll();
        List<KeyboardGetDto> keyboardsDto = new ArrayList<KeyboardGetDto>();
        for(int i = 0; i < keyboards.size(); i++){
            keyboardsDto.add(keyboardMapper.toDto(keyboards.get(i)));
        }
        return keyboardsDto;
    }

    public KeyboardGetDto getKeyboardDetail(Long keyboardId){
        Keyboard keyboard = keyboardRepository.findById(keyboardId)
                .orElseThrow(()->new NullPointerException("해당 모델을 찾을 수 없습니다."));
        return keyboardMapper.toDto(keyboard);
    }

    public KeyboardCreateDto addKeyboard(KeyboardCreateDto keyboard){
        Keyboard keyboardInfo = keyboard.toEntity();
        keyboardRepository.save(keyboardInfo);
        return keyboard;
    }

    public String deleteKeyboard(Long keyboardId){
        keyboardRepository.delete(keyboardRepository.findById(keyboardId)
                .orElseThrow(()->new NullPointerException("해당 모델을 찾을 수 없습니다.")));
        return "success";
    }

    public List<KeyboardGetDto> searchKeyboard(KeyboardSearchDto searchKeyboards){
        List<KeyboardGetDto> keyboards = new ArrayList<>();
        Specification<Keyboard> spec = Specification.where(KeyboardSpecification.equalKey(searchKeyboards));
        List<Keyboard> keyboardList = keyboardRepository.findAll(spec);
        for(int i = 0; i < keyboardList.size();i++){
            keyboards.add(keyboardMapper.toDto(keyboardList.get(i)));
        }
        return keyboards;
    }

    public List<KeyboardGetDto> filterByCategoryKeyboard(HashMap<String,String> keywords){
        String keyword = keywords.get("keyword");
        List<KeyboardGetDto> keyboards = keyboardRepository.findAllByKeyword(keyword);
        return keyboards;
    }

}
