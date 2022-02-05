package com.keyduck.keyboard.service;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.domain.KeyboardTags;
import com.keyduck.keyboard.domain.Tag;
import com.keyduck.keyboard.dto.*;
import com.keyduck.keyboard.repository.*;
import com.keyduck.mapper.KeyboardMapper;
import com.keyduck.mapper.SimpleKeyboardMapper;
import com.keyduck.review.domain.Review;
import com.keyduck.review.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class KeyboardService {
    private final KeyboardRepository keyboardRepository;
    private final KeyboardMapper keyboardMapper;
    private final SimpleKeyboardMapper simpleKeyboardMapper;
    private final TagRepository tagRepository;
    private final KeyboardTagRepository keyboardTagRepository;
    private final ReviewRepository reviewRepository;
    private final SearchRepository searchRepository;

    public KeyboardService(KeyboardRepository keyboardRepository, KeyboardMapper keyboardMapper, SimpleKeyboardMapper simpleKeyboardMapper, TagRepository tagRepository, KeyboardTagRepository keyboardTagRepository, ReviewRepository reviewRepository, SearchRepository searchRepository) {
        this.keyboardRepository = keyboardRepository;
        this.keyboardMapper = keyboardMapper;
        this.simpleKeyboardMapper = simpleKeyboardMapper;
        this.tagRepository = tagRepository;
        this.keyboardTagRepository = keyboardTagRepository;
        this.reviewRepository = reviewRepository;
        this.searchRepository = searchRepository;
    }

    public List<SimpleKeyboardDto> getAllKeyboards() {
        List<Keyboard> keyboards = keyboardRepository.findAll();
        List<SimpleKeyboardDto> keyboardsDto = new ArrayList<SimpleKeyboardDto>();
        for (int i = 0; i < keyboards.size(); i++) {
            keyboardsDto.add(keyboards.get(i).toDto());
        }
        return keyboardsDto;
    }

    public KeyboardGetDto getKeyboardDetail(Long keyboardId) {
        Keyboard keyboard = keyboardRepository.findById(keyboardId)
                .orElseThrow(() -> new NullPointerException("해당 모델을 찾을 수 없습니다."));
        calculateStarAverage(keyboard);
        return keyboardMapper.toDto(keyboard);
    }

    public KeyboardCreateDto addKeyboard(KeyboardCreateDto keyboard) throws ParseException {
        Keyboard keyboardInfo = keyboard.toEntity();
        keyboardRepository.save(keyboardInfo);
        searchRepository.save(keyboardInfo);
        findTag(keyboardInfo);
        return keyboard;
    }

    public String deleteKeyboard(Long keyboardId) {
        keyboardRepository.delete(keyboardRepository.findById(keyboardId)
                .orElseThrow(() -> new NullPointerException("해당 모델을 찾을 수 없습니다.")));
        return "success";
    }

    public List<SimpleKeyboardDto> filterKeyboard(KeyboardFilterDto filterKeyboards) {
        List<SimpleKeyboardDto> keyboards = new ArrayList<>();
        Specification<Keyboard> spec = Specification.where(KeyboardSpecification.equalKey(filterKeyboards));
        List<Keyboard> keyboardList = keyboardRepository.findAll(spec);
        for (int i = 0; i < keyboardList.size(); i++) {
            keyboards.add(simpleKeyboardMapper.toDto(keyboardList.get(i)));
        }
        return keyboards;
    }


    public List<SimpleKeyboardDto> filterWhileResult(HashMap<String, String> searchKeywords) {

        KeyboardFilterDto search = KeyboardFilterDto.KeyboardFilterDtoBuilder()
                .arrangement(Integer.parseInt(searchKeywords.get("arrangement")))
                .startPrice(Integer.parseInt(searchKeywords.get("startPrice")))
                .endPrice(Integer.parseInt(searchKeywords.get("endPrice")))
                .switchColor(new String[]{searchKeywords.get("switchColor")})
                .brand(searchKeywords.get("brand"))
                .build();

        return filterKeyboard(search);
    }

    private void findTag(Keyboard keyboardInfo){
        if(keyboardInfo.getArrangement().equals(108) && keyboardInfo.getSwitchColor().equals("적축")){
            keyboardTagRepository.save(new KeyboardTags(keyboardInfo,tagRepository.findByContent("키보드 치는 소리 안나게 해라")));
        }
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

    public List<RecommendKeyboardDto> getRecommend() {
        List<RecommendKeyboardDto> result = new ArrayList<>();
        List<Integer> indexList = Arrays.asList(1,2,3,4,5,6,7,8);
        for(int i = 0; i < 2; i++){
            RecommendKeyboardDto recommendKeyboardList = new RecommendKeyboardDto();
            List<KeyboardTags> keyboards = findKeyboardsByTag(recommendKeyboardList,indexList);
            for(KeyboardTags keyboardTags : keyboards){
                recommendKeyboardList.addKeyboard(keyboardTags.getKeyboard());
            }
            result.add(recommendKeyboardList);
        }

        RecommendKeyboardDto recentKeyboardsList = new RecommendKeyboardDto();
        Tag recentTag = new Tag("따끈따끈 신상");
        recentKeyboardsList.setTag(recentTag);
        List<Keyboard> recentKeyboards = keyboardRepository.findTop10ByOrderByDateDesc();
        for(Keyboard keyboard : recentKeyboards){
            recentKeyboardsList.addKeyboard(keyboard);
        }
        result.add(recentKeyboardsList);

        return result;
    }

    private Tag pickRandomTag(List<Integer> indexList){
        Random random = new Random();
        Long randomIndex = Long.valueOf(random.nextInt(indexList.size()));
        indexList.remove(randomIndex);
        Tag findTag = tagRepository.findByTagId(randomIndex);
        return findTag;
    }

    private List<KeyboardTags> findKeyboardsByTag(RecommendKeyboardDto recommendKeyboardList,List<Integer> indexList){
        List<KeyboardTags> keyboards = new ArrayList<>();
        Tag tag = null;
        while(keyboards.size() == 0){
            tag = pickRandomTag(indexList);
            keyboards = keyboardTagRepository.findFirst10ByTag(tag);
        }
        recommendKeyboardList.setTag(tag);
        return keyboards;
    }

    public List<Tag> addTag(HashMap<String,String> tag){
        String content = tag.get("content");
        tagRepository.save(new Tag(content));
        return tagRepository.findAll();
    }

    public void calculateStarAverage(Keyboard keyboard){
        List<Review> reviewList = reviewRepository.findAllByKeyboard(keyboard);
        if(keyboard.getStar() == null || reviewList.size() == 0){
            keyboard.setStar((float) 0);
        }
        else{
            float sumStar = (float)0;
            for(Review review : reviewList){
                sumStar += review.getStar();
            }
            sumStar /= reviewList.size();
            keyboard.setStar(sumStar);
        }
        keyboardRepository.save(keyboard);
    }

    public List<SimpleKeyboardDto> searchKeyboard(KeyboardSearchDto searchKeyboards) {
        Iterable<Keyboard> keyboards = searchRepository.findByInfoContains(searchKeyboards.getSearchKeyword());
        List<SimpleKeyboardDto> result = new ArrayList<>();
        for(Keyboard keyboard : keyboards){
            result.add(keyboard.toDto());
        }
        return result;
    }
}
