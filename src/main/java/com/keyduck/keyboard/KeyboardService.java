package com.keyduck.keyboard;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardCreateDto;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.mapper.KeyboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyboardService {
    private final KeyboardRepository keyboardRepository;
    @Autowired
    KeyboardMapper keyboardMapper;

    public List<KeyboardGetDto> getAllKeyboards(){
        List<Keyboard> keyboards = keyboardRepository.findAll();
        List<KeyboardGetDto> keyboards_dto = new ArrayList<KeyboardGetDto>();
        for(int i = 0; i < keyboards.size(); i++){
            keyboards_dto.add(keyboardMapper.toDto(keyboards.get(i)));
        }
        return keyboards_dto;
    }

    public KeyboardGetDto getKeyboardDetail(String model){
        Keyboard keyboard = keyboardRepository.findByModel(model).orElseThrow(()->new NullPointerException("모델을 찾을 수 없습니다."));
        return keyboardMapper.toDto(keyboard);
    }

    public KeyboardCreateDto addKeyboard(KeyboardCreateDto keyboard){
        Keyboard keyboardInfo = keyboard.toEntity();
        System.out.println(
                keyboardInfo.getBrand()
        );
        keyboardRepository.save(keyboardInfo);
        return keyboard;
    }

    public String deleteKeyboard(String model){
        keyboardRepository.delete(keyboardRepository.findByModel(model).orElseThrow(()->new NullPointerException()));
        return "success";
    }




}
