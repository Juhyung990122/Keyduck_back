package com.keyduck.keyboard.service;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardCreateDto;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.dto.KeyboardSearchDto;
import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.keyboard.repository.KeyboardSpecification;
import com.keyduck.mapper.KeyboardMapper;
import com.keyduck.mapper.SimpleKeyboardMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class KeyboardService {
    private final KeyboardRepository keyboardRepository;
    private final KeyboardMapper keyboardMapper;
    private final SimpleKeyboardMapper simpleKeyboardMapper;

    public KeyboardService(KeyboardRepository keyboardRepository, KeyboardMapper keyboardMapper, SimpleKeyboardMapper simpleKeyboardMapper) {
        this.keyboardRepository = keyboardRepository;
        this.keyboardMapper = keyboardMapper;
        this.simpleKeyboardMapper = simpleKeyboardMapper;
    }

    public List<SimpleKeyboardDto> getAllKeyboards() {
        List<Keyboard> keyboards = keyboardRepository.findAll();
        List<SimpleKeyboardDto> keyboardsDto = new ArrayList<SimpleKeyboardDto>();
        for (int i = 0; i < keyboards.size(); i++) {
            keyboardsDto.add(simpleKeyboardMapper.toDto(keyboards.get(i)));
        }
        return keyboardsDto;
    }

    public KeyboardGetDto getKeyboardDetail(Long keyboardId) {
        Keyboard keyboard = keyboardRepository.findById(keyboardId)
                .orElseThrow(() -> new NullPointerException("해당 모델을 찾을 수 없습니다."));
        return keyboardMapper.toDto(keyboard);
    }

    public KeyboardCreateDto addKeyboard(KeyboardCreateDto keyboard) throws ParseException {
        Keyboard keyboardInfo = keyboard.toEntity();
        keyboardRepository.save(keyboardInfo);
        System.out.println(keyboardInfo.getDate());
        return keyboard;
    }

    public String deleteKeyboard(Long keyboardId) {
        keyboardRepository.delete(keyboardRepository.findById(keyboardId)
                .orElseThrow(() -> new NullPointerException("해당 모델을 찾을 수 없습니다.")));
        return "success";
    }

    public List<SimpleKeyboardDto> searchKeyboard(KeyboardSearchDto searchKeyboards) {
        List<SimpleKeyboardDto> keyboards = new ArrayList<>();
        Specification<Keyboard> spec = Specification.where(KeyboardSpecification.equalKey(searchKeyboards));
        List<Keyboard> keyboardList = keyboardRepository.findAll(spec);
        for (int i = 0; i < keyboardList.size(); i++) {
            keyboards.add(simpleKeyboardMapper.toDto(keyboardList.get(i)));
        }
        return keyboards;
    }


    public List<SimpleKeyboardDto> searchWhileResult(HashMap<String, String> searchKeywords) {

        KeyboardSearchDto search = KeyboardSearchDto.KeyboardSearchDtoBuilder()
                .arrangement(Integer.parseInt(searchKeywords.get("arrangement")))
                .startPrice(Integer.parseInt(searchKeywords.get("startPrice")))
                .endPrice(Integer.parseInt(searchKeywords.get("endPrice")))
                .switchColor(new String[]{searchKeywords.get("switchColor")})
                .brand(searchKeywords.get("brand"))
                .build();

        return searchKeyboard(search);
    }
}
