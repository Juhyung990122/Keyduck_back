package com.keyduck.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.member.domain.Member;
import com.keyduck.member.repository.MemberRepository;
import com.keyduck.review.domain.Review;
import com.keyduck.review.dto.ReviewCreateDto;
import com.keyduck.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private ObjectMapper mapper;
    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private KeyboardRepository keyboardRepository;
    @Autowired
    private MemberRepository memberRepository;

    Keyboard testKeyboard = Keyboard.KeyboardBuilder().build();
    Member testMember = Member.MemberBuilder()
            .accessToken("testToken")
            .build();


    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilter(((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })).build();

        keyboardRepository.save(testKeyboard);
        memberRepository.save(testMember);
    }

    @Test
    public void review생성_성공() throws Exception {
        ReviewCreateDto reviewCreateDto = new ReviewCreateDto(testKeyboard.getKeyboardId(), 3.5F,"테스트 리뷰");
        MockHttpServletResponse response = mvc.perform(post("/v1/reviews/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-AUTH-TOKEN","testToken")
                .content(mapper.writeValueAsString(reviewCreateDto)))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(),is(HttpStatus.OK.value()));
    }

    @Test
    public void 모델별review조회_성공() throws Exception{
        Review testReview = Review.ReviewBuilder()
                .keyboard(testKeyboard)
                .content("테스트리뷰")
                .build();
        reviewRepository.save(testReview);
        String successData = "{ \"keyboardId\" :"+ testKeyboard.getKeyboardId() +"}";
        mvc.perform(get("/v1/review?keyboardId="+ testKeyboard.getKeyboardId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(successData))
                .andExpect(status().isOk())
                .andDo(print());
        reviewRepository.delete(testReview);
    }

    @Test
    public void 멤버별review조회_성공() throws Exception{
        Review testReview = Review.ReviewBuilder()
                .member(testMember)
                .content("테스트리뷰")
                .build();
        reviewRepository.save(testReview);
        String successData = "{ \"memberId\" :"+testMember.getMemId()+"}";
        mvc.perform(get("/v1/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(successData))
                .andExpect(status().isOk())
                .andDo(print());
        reviewRepository.delete(testReview);
    }

    @Test
    public void review디테일_성공() throws Exception{
        Review testReview = Review.ReviewBuilder()
                .content("테스트리뷰")
                .build();
        reviewRepository.save(testReview);
        mvc.perform(get("/v1/review/"+testReview.getReviewId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        reviewRepository.delete(testReview);
    }

    @Test
    public void  review삭제_성공() throws  Exception{
        Review testReview = mock(Review.class);
        mvc.perform(delete("/v1/review/delete/"+testReview.getReviewId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @After
    public void after(){
//        keyboardRepository.delete(testKeyboard);
//        memberRepository.delete(testMember);
    }


}