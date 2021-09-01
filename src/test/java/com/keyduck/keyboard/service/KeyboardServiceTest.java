package com.keyduck.keyboard.service;


import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.mapper.KeyboardMapper;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardServiceTest {

    @Mock
    KeyboardRepository keyboardRepository;
    @Mock
    KeyboardMapper keyboardMapper;
    @InjectMocks
    KeyboardService keyboardService;

    @Before
    public void setup(){
        keyboardService = new KeyboardService(keyboardRepository,keyboardMapper);
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
        given(keyboardMapper.toDto(any(Keyboard.class))).willReturn(new KeyboardGetDto());
        //when
        List<KeyboardGetDto> request = keyboardService.getAllKeyboards();
        //then
        assertThat(request.size(),is(2));
        assertThat(request.get(0).getKeyboardId(),is(keyboard1.getKeyboardId()));
        assertThat(request.get(1).getKeyboardId(),is(keyboard2.getKeyboardId()));
    }

    @Test
    public void getKeyboardDetail() {
    }

    @Test
    public void addKeyboard() {
    }

    @Test
    public void deleteKeyboard() {
    }

    @Test
    public void searchKeyboard() {
    }

    @Test
    public void filterByCategoryKeyboard() {
    }

}
