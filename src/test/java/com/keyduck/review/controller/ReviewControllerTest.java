package com.keyduck.review.controller;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.member.domain.Member;
import com.keyduck.member.repository.MemberRepository;
import com.keyduck.review.domain.Review;
import com.keyduck.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
    private WebApplicationContext ctx;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private KeyboardRepository keyboardRepository;
    @Autowired
    private MemberRepository memberRepository;

    Keyboard testKeyboard = Keyboard.KeyboardBuilder().build();
    Member testMember = Member.MemberBuilder().build();

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
    //수정예정
    @WithMockUser(username = "일반유저", roles = "USER")
    public void review생성_성공() throws Exception {
        String successData = "{\n" +
                "\"name\":"+testKeyboard.getKeyboardId()+",\n"+
                "\"star\":3.5,\n" +
                "\"author\":"+testMember.getMemberId()+",\n" +
                "\"content\":\"테스트리뷰입니다.\"\n" +
                "}";
        mvc.perform(post("/v1/review/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(successData))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void 모델별review조회_성공() throws Exception{
        Review testReview = Review.ReviewBuilder()
                .name(testKeyboard)
                .content("테스트리뷰")
                .build();
        reviewRepository.save(testReview);
        String successData = "{ \"keyboardId\" :"+ testKeyboard.getKeyboardId() +"}";
        mvc.perform(get("/v1/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(successData))
                .andExpect(status().isOk())
                .andDo(print());
        reviewRepository.delete(testReview);
    }

    @Test
    public void 멤버별review조회_성공() throws Exception{
        Review testReview = Review.ReviewBuilder()
                .author(testMember)
                .content("테스트리뷰")
                .build();
        reviewRepository.save(testReview);
        String successData = "{ \"memberId\" :"+testMember.getMemberId()+"}";
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
        keyboardRepository.delete(testKeyboard);
        memberRepository.delete(testMember);
    }


}