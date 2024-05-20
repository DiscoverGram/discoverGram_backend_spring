package com.ssafy.enjoytrip.member.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.domain.member.controller.MemberController;
import com.ssafy.enjoytrip.domain.member.dto.MemberRequestDto;
import com.ssafy.enjoytrip.domain.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.domain.member.dto.MemberUpdateDto;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private MemberResponseDto memberUpdatedDto;
    private MemberUpdateDto memberUpdateDto;

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

        memberUpdateDto = MemberUpdateDto.builder()
                .name("이현규")
                .password(passwordEncoder.encode("5678"))
                .build();

        memberUpdatedDto = MemberResponseDto.builder()
                .id("song")
                .name("이현규")
                .password(passwordEncoder.encode("5678"))
                .build();
    }

    @Test
    @DisplayName("회원가입")
    void signUp() throws Exception{

        given(memberService.signUp(memberRequestDto)).willReturn(memberRequestDto.getId());

        mockMvc.perform(post("/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원 조회")
    void detail() throws Exception{
        // given
        when(memberService.detailMember(1L)).thenReturn(memberResponseDto);

        // when
        mockMvc.perform(get("/members/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("회원 수정")
    void update() throws Exception{
        // given
        when(memberService.updateMember(memberUpdateDto, 1L)).thenReturn(memberUpdatedDto);

        // when
        mockMvc.perform(put("/members/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberUpdateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원 삭제")
    void deleteMember() throws Exception{
        // given
        when(memberService.deleteMember(1L)).thenReturn(1);

        // when
        mockMvc.perform(delete("/members/1")
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}