package com.ssafy.enjoytrip.member.service;

import com.ssafy.enjoytrip.domain.member.service.MemberService;
import com.ssafy.enjoytrip.global.error.exception.UserExistException;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.member.dto.MemberRequestDto;
import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    MemberService memberService;

    MemberRequestDto requestDto;
    Member member;

    @BeforeEach
    void init(){
        requestDto = MemberRequestDto.builder()
                .id("song")
                .name("송도언")
                .password(passwordEncoder.encode("1234"))
                .build();
        member = Member.builder()
                .id("song")
                .name("송도언")
                .password(passwordEncoder.encode("1234"))
                .build();
    }

    @Test
    @DisplayName("정상적인 회원가입")
    void signUp() {
        when(memberRepository.save(any())).thenReturn(member);

        String expect = memberService.signUp(requestDto);

        assertThat(expect).isEqualTo(requestDto.getId());
    }

    @Test
    @DisplayName("이미 존재하는 아이디")
    void AlreadyExistUsername() {
        when(memberRepository.existsById("song")).thenReturn(true);
        UserExistException exception = assertThrows(UserExistException.class,
                () -> memberService.signUp(requestDto));

        String message = exception.getErrorCode().getMessage();
        assertThat(message).isEqualTo("User Already Exist");
    }
}