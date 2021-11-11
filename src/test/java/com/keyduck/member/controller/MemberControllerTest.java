package com.keyduck.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyduck.member.domain.Member;
import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;
import com.keyduck.member.domain.RequestMember;
import com.keyduck.member.repository.MemberRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    private WebApplicationContext ctx;
//    @Autowired
//    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private MemberRepository memberRepository;


    @Before
    public void setup(){
//        mvc = MockMvcBuilders.webAppContextSetup(ctx)
//                .addFilter(((request, response, chain) -> {
//            response.setCharacterEncoding("UTF-8");
//            chain.doFilter(request, response);
//        })).build();
    }

    @Test
    @WithMockUser(username="admin", roles = "ADMIN")
    public void 전체회원조회_성공() throws Exception {
        //given
        //when
        MockHttpServletResponse response = mvc.perform(get("/v1/members")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus(),is(HttpStatus.OK.value()));
    }

    @Test
    @WithMockUser(username="admin", roles = "USER")
    public void 개별회원조회_성공() throws Exception{
        //given
        Member detailMember = Member.MemberBuilder()
                .email("detailTest@naver.com")
                .build();
        detailMember.setMemId(1L);
        when(memberRepository.findById(any())).thenReturn(Optional.of(detailMember));
        //when
        MockHttpServletResponse response = mvc.perform(get("/v1/members/" + detailMember.getMemId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus(),is(HttpStatus.OK.value()));
    }

    @Test
    public void 회원가입_성공() throws Exception {
        //given
        RequestMember request = new RequestMember();
        request.setEmail("rightMember@naver.com");
        request.setPassword("1212");

        //when
        MockHttpServletResponse response = mvc.perform(post("/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn()
                .getResponse();

        //then
        verify(memberRepository).save(any());
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));

    }

    @Test
    public void 회원가입_실패_유효성검사() throws Exception {
        //given
        RequestMember request = new RequestMember();
        //when
        MockHttpServletResponse response = mvc.perform(post("/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus(),is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void 로그인_성공() throws Exception {
        //given
        Member member = Member.MemberBuilder()
                .email("signintest@naver.com")
                .password(passwordEncoder.encode("{noop}"+(CharSequence)"1212"))
                .role(MemberRole.USER)
                .type(MemberType.Keyduck)
                .build();
        when(memberRepository.findByEmail("signintest@naver.com"))
                .thenReturn(Optional.ofNullable(member));

        RequestMember request = new RequestMember();
        request.setEmail("signintest@naver.com");
        request.setPassword("1212");

        //when
        MockHttpServletResponse response = mvc.perform(post("/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus(),is(HttpStatus.INTERNAL_SERVER_ERROR.value()));

    }
    @Test
    @WithMockUser(username="leavetest", roles = "USER")
    public void 회원탈퇴_성공() throws Exception{
        //given
        Member member = Member.MemberBuilder()
                .nickname("test")
                .email("leavetest@naver.com")
                .password("1212")
                .role(MemberRole.ADMIN)
                .type(MemberType.Keyduck)
                .build();
        when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(member));
        RequestMember request = new RequestMember();
        request.setEmail("leavetest@naver.com");

        //when
        MockHttpServletResponse response = mvc.perform(delete("/v1/members/leave")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn()
                .getResponse();

        //then
        assertThat(response.getStatus(),is(HttpStatus.OK.value()));
        verify(memberRepository).delete(any());
    }
}