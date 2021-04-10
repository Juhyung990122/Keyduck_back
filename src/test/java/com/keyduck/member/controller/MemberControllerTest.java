package com.keyduck.member.controller;

import com.keyduck.member.domain.Member;
import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;
import com.keyduck.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class MemberControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilter(((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })).build();
    }
    @Test
    public void getAllMembers() throws Exception {
        mvc.perform(get("/v1/members")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getMemberDetail() throws Exception{
        //성공
        mvc.perform(get("/v1/members/8")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
        //실패
        mvc.perform(get("/v1/members/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    public void signup() throws Exception {
        // 성공
        String rightMember = "{\"email\":\"rightMember\",\"password\":\"1212\",\"role\":\"USER\",\"type\":\"Keyduck\"}";
        mvc.perform(post("/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rightMember))
                .andExpect(status().isOk());
        // 실패(유효성)
        String wrongValidationMember = "{\"email\":\" wrongValidationMember\",\"password\":\"1212\",\"role\":\"USER\"}";
        mvc.perform(post("/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(wrongValidationMember))
                .andExpect(status().isBadRequest());
        // 실패(이미 가입된 회원)
        String duplicateMember = "{\"email\":\"rightMember\",\"password\":\"1212\",\"role\":\"USER\",\"type\":\"Keyduck\"}";
        mvc.perform(post("/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(duplicateMember))
                .andExpect(status().isInternalServerError());

        memberRepository.delete(memberRepository.findByEmail("rightMember").orElse(null));

    }
    @Test
    public void signin() throws Exception {
        Member member = Member.MemberBuilder()
                .nickname("test")
                .email("signintest@naver.com")
                .password(passwordEncoder.encode("{noop}"+(CharSequence)"1212"))
                .role(MemberRole.ADMIN)
                .type(MemberType.Keyduck)
                .build();
        memberRepository.save(member);
        mvc.perform(post("/v1/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"signintest@naver.com\",\"password\":\"1212\"}"))
                .andExpect(status().isOk());
        memberRepository.delete(member);
    }
    @Test
    public void leave() throws Exception{
        Member member = Member.MemberBuilder()
                .nickname("test")
                .email("leavetest@naver.com")
                .password("1212")
                .role(MemberRole.ADMIN)
                .type(MemberType.Keyduck)
                .build();
        memberRepository.save(member);
        mvc.perform(delete("/v1/members/leave")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":"+"\""+member.getEmail()+"\"}"))
                .andExpect(status().isOk());
    }

}