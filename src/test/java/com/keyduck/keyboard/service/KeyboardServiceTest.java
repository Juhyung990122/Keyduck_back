package com.keyduck.keyboard.service;


import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardCreateDto;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.keyboard.repository.TagRepository;
import com.keyduck.mapper.KeyboardMapper;
import com.keyduck.mapper.SimpleKeyboardMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardServiceTest {

    @Mock
    KeyboardRepository keyboardRepository;
    @Mock
    KeyboardMapper keyboardMapper;
    @Mock
    SimpleKeyboardMapper simpleKeyboardMapper;
    @Mock
    TagRepository tagRepository;
    @InjectMocks
    KeyboardService keyboardService;

    @Before
    public void setup(){
        keyboardService = new KeyboardService(keyboardRepository,keyboardMapper,simpleKeyboardMapper, tagRepository);
    }


    @Test
    public void getAllKeyboards_모든_키보드를_불러온다() {
        //given
        Keyboard keyboard1 = Keyboard.KeyboardBuilder().build();
        Keyboard keyboard2 = Keyboard.KeyboardBuilder().build();
        List<Keyboard> keyboards = new ArrayList<>();
        keyboards.add(keyboard1);
        keyboards.add(keyboard2);
        given(keyboardRepository.findAll()).willReturn(keyboards);
        given(simpleKeyboardMapper.toDto(any(Keyboard.class))).willReturn(new SimpleKeyboardDto());
        //when
        List<SimpleKeyboardDto> request = keyboardService.getAllKeyboards();
        //then
        assertThat(request.size(),is(2));
        assertThat(request.get(0).getKeyboardId(),is(keyboard1.getKeyboardId()));
        assertThat(request.get(1).getKeyboardId(),is(keyboard2.getKeyboardId()));
    }

    @Test
    public void getKeyboardDetail_키보드_하나의_디테일을_불러온다() {
        //given
        Keyboard keyboard = Keyboard.KeyboardBuilder().build();
        keyboard.setKeyboardId(0L);
        KeyboardGetDto keyboardGetDto = new KeyboardGetDto();
        keyboardGetDto.setKeyboardId(0L);
        given(keyboardRepository.findById(keyboard.getKeyboardId())).willReturn(Optional.of(keyboard));
        given(keyboardMapper.toDto(any(Keyboard.class))).willReturn(keyboardGetDto);

        //when
        KeyboardGetDto request = keyboardService.getKeyboardDetail(keyboard.getKeyboardId());

        //then
        assertThat(request.getKeyboardId(),is(keyboard.getKeyboardId()));

    }

    @Test
    public void addKeyboard() throws ParseException {
        //given
        KeyboardCreateDto keyboardCreateDto = new KeyboardCreateDto();

        //when
        keyboardService.addKeyboard(keyboardCreateDto);

        //then
        verify(keyboardRepository).save(any());
    }

    @Test
    public void deleteKeyboard() {
    }

    @Test
    public void searchKeyboard(){

    }

    @Test
    public void filterByCategoryKeyboard() {
    }

}
