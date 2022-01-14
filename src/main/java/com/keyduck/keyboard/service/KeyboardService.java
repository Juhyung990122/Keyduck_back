package com.keyduck.keyboard.service;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.domain.KeyboardTags;
import com.keyduck.keyboard.dto.KeyboardCreateDto;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.dto.KeyboardSearchDto;
import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.keyboard.repository.KeyboardSpecification;
import com.keyduck.keyboard.repository.KeyboardTagRepository;
import com.keyduck.keyboard.repository.TagRepository;
import com.keyduck.mapper.KeyboardMapper;
import com.keyduck.mapper.SimpleKeyboardMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class KeyboardService {
    private final KeyboardRepository keyboardRepository;
    private final KeyboardMapper keyboardMapper;
    private final SimpleKeyboardMapper simpleKeyboardMapper;
    private final TagRepository tagRepository;
    private final KeyboardTagRepository keyboardTagRepository;

    public KeyboardService(KeyboardRepository keyboardRepository, KeyboardMapper keyboardMapper, SimpleKeyboardMapper simpleKeyboardMapper, TagRepository tagRepository, KeyboardTagRepository keyboardTagRepository) {
        this.keyboardRepository = keyboardRepository;
        this.keyboardMapper = keyboardMapper;
        this.simpleKeyboardMapper = simpleKeyboardMapper;
        this.tagRepository = tagRepository;
        this.keyboardTagRepository = keyboardTagRepository;
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
        findTag(keyboardInfo);
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

    private void findTag(Keyboard keyboardInfo){
        if(keyboardInfo.getLed() != null && keyboardInfo.getSwitchColor().equals("청축")){
            keyboardTagRepository.save(new KeyboardTags(keyboardInfo,tagRepository.findByContent("피지컬이 좋아지는 기분")));
        }
        if(keyboardInfo.getLed().equals("레인보우 백라이트") || keyboardInfo.getLed().equals("RGB 백라이트")){
            keyboardTagRepository.save(new KeyboardTags(keyboardInfo,tagRepository.findByContent("웅장해지는 조명")));
        }
        if(keyboardInfo.getLed().equals("단색 백라이트") || keyboardInfo.getLed() == null){
            keyboardTagRepository.save(new KeyboardTags(keyboardInfo,tagRepository.findByContent("화려함의 끝은 순정")));
        }
        if(keyboardInfo.getConnect().contains("블루투스")){
            keyboardTagRepository.save(new KeyboardTags(keyboardInfo,tagRepository.findByContent("하나로는 부족해..")));
        }
        //True일지도..?
        if(keyboardInfo.getHotswap() != null){
            keyboardTagRepository.save(new KeyboardTags(keyboardInfo,tagRepository.findByContent("스위치 유목민")));
        }
        if(keyboardInfo.getArrangement() < 87 && keyboardInfo.getWeight() <= 600){
            keyboardTagRepository.save(new KeyboardTags(keyboardInfo,tagRepository.findByContent("언제 어디서든")));
        }
        if(keyboardInfo.getPrice() <= 60000){
            keyboardTagRepository.save(new KeyboardTags(keyboardInfo,tagRepository.findByContent("키보드가 처음이라면")));
        }
        if(keyboardInfo.getPrice() <= 190000 || 100000 <= keyboardInfo.getPrice()){
            keyboardTagRepository.save(new KeyboardTags(keyboardInfo,tagRepository.findByContent("기왕 살 거 좋은걸로")));
        }
    }

    public List<SimpleKeyboardDto> findRecent() {
        List<Keyboard> keyboards = keyboardRepository.findTop10ByOrderByDateDesc();
        List<SimpleKeyboardDto> result = new ArrayList<>();
        for(Keyboard keyboard : keyboards){
            result.add(simpleKeyboardMapper.toDto(keyboard));
        }
        return result;
    }
}
