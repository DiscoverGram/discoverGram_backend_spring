package com.ssafy.enjoytrip.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.member.dto.MemberRequestDto;
import com.ssafy.enjoytrip.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@ExtendWith(SpringExtension.class)
@WithMockUser
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    PasswordEncoder passwordEncoder;
    ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private MemberService memberService;

    private MemberRequestDto memberRequestDto;
    private MemberResponseDto memberResponseDto;

    @BeforeEach
    void init() {
        memberRequestDto = MemberRequestDto.builder()
                .id("song")
                .name("송도언")
                .password(passwordEncoder.encode("1234"))
                .build();

        memberResponseDto = MemberResponseDto.builder()
                .id("song")
                .name("송도언")
                .password(passwordEncoder.encode("1234"))
                .build();

    }

    @Test
    @DisplayName("회원가입")
    void signUp() throws Exception{

        given(memberService.signUp(memberRequestDto)).willReturn(memberRequestDto.getId());

        mockMvc.perform(post("/sign-up")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}