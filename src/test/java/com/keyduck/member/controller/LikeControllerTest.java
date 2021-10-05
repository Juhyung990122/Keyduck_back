package com.keyduck.member.controller;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.member.domain.Member;
import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;
import com.keyduck.member.repository.MemberRepository;
import com.keyduck.member.service.LikesService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class LikeControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private KeyboardRepository keyboardRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LikesService likesService;


    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilter(((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })).build();
    }


    @Test
    public void getLikes_좋아요한_키보드_불러오기_성공() throws Exception {
        Keyboard keyboard = Keyboard.KeyboardBuilder().build();
        keyboardRepository.save(keyboard);

        Member member = Member.MemberBuilder()
                .nickname("test")
                .email("liketest@naver.com")
                .password("1212")
                .role(MemberRole.ADMIN)
                .type(MemberType.Keyduck)
                .likes(new ArrayList<Keyboard>())
                .build();
        memberRepository.save(member);
        likesService.addLikes(member.getMemId(),keyboard.getKeyboardId());

        mvc.perform(get("/v1/"+member.getMemId()+"/likes"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"success\":true,\"code\":0,\"msg\":\"성공했습니다.\",\"data\":[{\"keyboardId\":null,\"thumbnailImg\":null,\"name\":null,\"price\":null,\"star\":null}]}"))
                .andDo(print());

        likesService.deleteLikes(member.getMemId(), keyboard.getKeyboardId());
        memberRepository.delete(member);
        keyboardRepository.delete(keyboard);
    }

    @Test
    public void addLikes_키보드_좋아요_추가성공() throws Exception {
        Keyboard keyboard = Keyboard.KeyboardBuilder().build();
        keyboardRepository.save(keyboard);

        Member member = Member.MemberBuilder()
                .nickname("test")
                .email("liketest@naver.com")
                .password("1212")
                .role(MemberRole.ADMIN)
                .type(MemberType.Keyduck)
                .likes(new ArrayList<Keyboard>())
                .build();
        memberRepository.save(member);

        mvc.perform(post("/v1/"+member.getMemId()+"/likes")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"keyboardId\":"+keyboard.getKeyboardId()+"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"success\":true,\"code\":0,\"msg\":\"성공했습니다.\",\"data\":\"좋아요 추가 성공했습니다.\"}"))
                .andDo(print());

        likesService.deleteLikes(member.getMemId(), keyboard.getKeyboardId());
        memberRepository.delete(member);
        keyboardRepository.delete(keyboard);
    }

    @Test
    public void addLikes_키보드_좋아요_취소성공() throws Exception {
        Keyboard keyboard = Keyboard.KeyboardBuilder().build();
        keyboardRepository.save(keyboard);

        Member member = Member.MemberBuilder()
                .nickname("test")
                .email("liketest@naver.com")
                .password("1212")
                .role(MemberRole.ADMIN)
                .type(MemberType.Keyduck)
                .likes(new ArrayList<Keyboard>())
                .build();
        memberRepository.save(member);

        mvc.perform(delete("/v1/"+member.getMemId()+"/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"keyboardId\":"+keyboard.getKeyboardId()+"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"success\":true,\"code\":0,\"msg\":\"성공했습니다.\",\"data\":\"좋아요 취소 성공했습니다.\"}"))
               .andDo(print());

        likesService.deleteLikes(member.getMemId(), keyboard.getKeyboardId());
        memberRepository.delete(member);
        keyboardRepository.delete(keyboard);
    }

}