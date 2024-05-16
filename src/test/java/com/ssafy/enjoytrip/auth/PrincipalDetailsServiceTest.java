package com.ssafy.enjoytrip.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.auth.dto.LoginRequestDto;
import com.ssafy.enjoytrip.member.domain.Member;
import com.ssafy.enjoytrip.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PrincipalDetailsServiceTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    PrincipalDetailsService principalDetailsService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    ObjectMapper objectMapper = new ObjectMapper();
    LoginRequestDto loginRequestDto;
    Member member;

    @BeforeEach
    void init(){
        loginRequestDto = LoginRequestDto.builder()
                .id("userId")
                .password("1234")
                .build();
        member = Member.builder()
                .id("userId")
                .name("송동동")
                .password(passwordEncoder.encode("1234"))
                .role("ROLE_USER")
                .build();
    }


    @Test
    @DisplayName("로그인")
    public void login() throws Exception{
        memberRepository.save(member);
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }
    @Test
    @DisplayName("로그아웃")
    public void logout() throws Exception{
        mvc.perform(delete("/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}