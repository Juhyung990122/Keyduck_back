package com.keyduck.review.controller;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.review.domain.Review;
import com.keyduck.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class ReviewControllerTest {

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
    @WithMockUser(username = "일반유저", roles = "USER")
    public void review생성_성공() throws Exception {
        String successData = "{\n" +
                "\"star\":3.5,\n" +
                "\"author\":\"test1@naver.com\",\n" +
                "\"content\":\"테스트리뷰 성공케이스입니다.\"\n" +
                "}";
        mvc.perform(post("/v1/review/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(successData))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    public void review생성_실패_미회원유저() throws Exception {
        String failData = "{\n" +
                "\"star\":3.5,\n" +
                "\"author\":\"anonymous@naver.com\",\n" +
                "\"content\":\"테스트리뷰 실패케이스입니다.(등록 회원 아님)\"\n" +
                "}";
        mvc.perform(post("/v1/review/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(failData))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test()
    public void 모델별review조회_성공() throws Exception{
        Keyboard testKeyboard = Keyboard.KeyboardBuilder()
                .model("테스트키보드")
                .build();
        keyboardRepository.save(testKeyboard);
        String model = "{ \"model\" : \"테스트키보드\"}";
        mvc.perform(get("/v1/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(model))
                .andExpect(status().isOk())
                .andDo(print());
        keyboardRepository.delete(testKeyboard);
    }


}