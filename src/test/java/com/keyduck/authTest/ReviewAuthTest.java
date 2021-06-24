package com.keyduck.authTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewAuthTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    private WebApplicationContext ctx;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilter(((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })).build();

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

}
