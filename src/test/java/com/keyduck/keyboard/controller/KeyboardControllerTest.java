package com.keyduck.keyboard.controller;

import com.keyduck.keyboard.repository.KeyboardRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class KeyboardControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    private KeyboardRepository keyboardRepository;


    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilter(((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })).build();
    }

    @Test
    public void Keyboard_전체조회() throws Exception{
        mvc.perform(get("/v1/keyboards/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void Keyboard_디테일조회() throws Exception{
        mvc.perform(get("/v1/keyboards/테스트키보드/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void Keyboard_추가() throws Exception {
        String successData = "{\"model\": \"테스트키보드\", \"brand\": \"ABKO\", \"connect\": \"유선\", \"switchBrand\": \"오테뮤\", \"switchColor\": \"청축\", \"hotswap\": 0, \"price\": 12000, \"keycap\": \"이중사출 키캡\", \"keycapImprint\": \"한글 정각\", \"keycapProfile\": \"default\", \"led\": \"레인보우 백라이트\", \"arrangement\": 104, \"weight\": 1050, \"cable\": \"찰탁식\", \"photo\": \"default\",\"keyword\":\"default\"}";
        mvc.perform(post("/v1/keyboards/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(successData))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void Keyboard_삭제() throws Exception{
        String successData = "{\"model\":\"테스트키보드\"}";
        mvc.perform(delete("/v1/keyboards/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(successData))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    public void Keyboard_검색() throws Exception{
        String successDataKeyword = "{\"keyword\" : [\"brand\":\"로지텍\"] }";
        String  successDataKeywords = "{\"keyword\" : [\"brand\",\"로지텍\",\"connect\":\"유선\"]}";

        mvc.perform(post("/v1/keyboards/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(successDataKeyword))
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(post("/v1/keyboards/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(successDataKeywords))
                .andExpect(status().isOk())
                .andDo(print());
    }

}