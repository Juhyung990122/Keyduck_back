package com.keyduck.keyboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyduck.keyboard.Request.RequestDeleteKeyboard;
import com.keyduck.keyboard.Request.RequestKeyboard;
import com.keyduck.keyboard.Request.RequestSearchKeyboard;
import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardSearchDto;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.spring.web.json.Json;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class KeyboardControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private KeyboardRepository keyboardRepository;

    @Test
    public void 키보드_전체조회_성공() throws Exception{
        //given
        //when
        MockHttpServletResponse response = mvc.perform(get("/v1/keyboards/")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    public void 키보드_디테일조회_성공() throws Exception{
        // given
        Keyboard detailKeyboard = Keyboard.KeyboardBuilder().build();
        detailKeyboard.setKeyboardId(1L);
        when(keyboardRepository.findById(any())).thenReturn(Optional.of(detailKeyboard));
        // when
        MockHttpServletResponse response = mvc.perform(get("/v1/keyboards/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus(),is(HttpStatus.OK.value()));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void 키보드_추가_성공() throws Exception {
        //{"model": "테스트키보드", "brand": "ABKO", "connect": "유선", "switchBrand":"오테뮤", "switchColor":"청축", "hotswap": 0, "price": 12000, "keycap": "이중사출 키캡", "keycapImprint": "한글 정각", "keycapProfile": "default", "led": "레인보우 백라이트", "arrangement": 104,"weight": 1050, "cable": "찰탁식", "photo": "default","keyword":"default"}
        // given
        RequestKeyboard requestKeyboard = new RequestKeyboard();
        requestKeyboard.setName("테스트키보드");
        Keyboard testKeyboard = new Keyboard();
        when(keyboardRepository.save(any())).thenReturn(testKeyboard);
        // when
        MockHttpServletResponse response = mvc.perform(post("/v1/keyboards/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestKeyboard)))
                .andReturn()
                .getResponse();
        // then
        assertThat(response.getStatus(),is(HttpStatus.OK.value()));
        verify(keyboardRepository).save(any());

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void 키보드_삭제_성공() throws Exception{
        //given
        Keyboard testKeyboard = Keyboard.KeyboardBuilder().build();
        testKeyboard.setKeyboardId(1L);
        when(keyboardRepository.findById(any())).thenReturn(Optional.of(testKeyboard));

        //when
        MockHttpServletResponse response = mvc.perform(delete("/v1/keyboards/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        verify(keyboardRepository).delete(any());

    }

    @Test
    public void 키보드_반복필터_성공() throws Exception{
        //given
        //TODO: 키보드 세팅
        //TODO: 키보드 검색키워드 -> {
        //	"arrangement" : 104,
        //	"brand" : "앱코 HACKER",
        //	"switchColor":"적축",
        //	"startPrice" : 0,
        //	"endPrice": 10000(해당조건에 부합하는 값이 없을땐 전체) & 50000(부분필터)
        //}

    }

}