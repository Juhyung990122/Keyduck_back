package com.keyduck.member.controller;

import com.keyduck.member.domain.Member;
import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;
import com.keyduck.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
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
    @WithMockUser(username="admin", roles = "ADMIN")
    public void getAllMembers() throws Exception {
        mvc.perform(get("/v1/members")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username="admin", roles = "ADMIN")
    public void getMemberDetail() throws Exception{
        final Member detailMember = Member.MemberBuilder().build();
        memberRepository.save(detailMember);
        mvc.perform(get("/v1/members/"+detailMember.getMemberId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        memberRepository.delete(detailMember);

    }

    @Test
    @WithAnonymousUser
    public void signup() throws Exception {
        // 성공
        String rightMember = "{\"email\":\"rightMember@naver.com\",\"password\":\"1212\",\"role\":\"USER\",\"type\":\"Keyduck\"}";
        mvc.perform(post("/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rightMember))
                .andExpect(status().isOk());
        // 실패(유효성)
        String wrongValidationMember = "{\"email\":\"wrongValidationMember@naver.com\",\"password\":\"1212\",\"role\":\"USER\"}";
        mvc.perform(post("/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(wrongValidationMember))
                .andExpect(status().isBadRequest());
        // 실패(이미 가입된 회원)
        String duplicateMember = "{\"email\":\"rightMember@naver.com\",\"password\":\"1212\",\"role\":\"USER\",\"type\":\"Keyduck\"}";
        mvc.perform(post("/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(duplicateMember))
                .andExpect(status().isInternalServerError());

        memberRepository.delete(memberRepository.findByEmail("rightMember@naver.com").orElse(null));

    }
    @Test
    @WithAnonymousUser
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
        memberRepository.delete(memberRepository.findByEmail("signintest@naver.com").orElse(null));
    }
    @Test
    @WithMockUser(username="admin", roles = "ADMIN")
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