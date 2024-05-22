package com.ssafy.enjoytrip.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.global.auth.PrincipalDetailsService;
import com.ssafy.enjoytrip.global.auth.dto.LoginRequestDto;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    LoginRequestDto notMatchedPasswordRequestDto;
    Member member;


    @BeforeEach
    void init(){
        loginRequestDto = LoginRequestDto.builder()
                .id("userId")
                .password("1234")
                .build();
        notMatchedPasswordRequestDto = LoginRequestDto.builder()
                .id("userId")
                .password("1111")
                .build();
        member = Member.builder()
                .id("userId")
                .name("송동동")
                .password(passwordEncoder.encode("1234"))
                .userProfileImage("/default.jpg")
                .role("ROLE_USER")
                .build();
    }
    @Test
    @DisplayName("로그인 성공")
    @Order(1)
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
    @Order(2)
    @DisplayName("잘못된 비밀번호로 로그인")
    public void NotMatchedPassword() throws Exception{
        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notMatchedPasswordRequestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @Order(3)
    @DisplayName("로그아웃")
    public void logout() throws Exception{
        mvc.perform(post("/logout"))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andExpect(status().is3xxRedirection());

    }
}