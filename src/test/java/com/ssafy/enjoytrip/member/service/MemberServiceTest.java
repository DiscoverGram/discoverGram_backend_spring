package com.ssafy.enjoytrip.member.service;

import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.member.dto.MemberRequestDto;
import com.ssafy.enjoytrip.domain.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.domain.member.dto.MemberUpdateDto;
import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import com.ssafy.enjoytrip.global.error.exception.UserExistException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
class MemberServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    MemberService memberService;
    @Mock
    MultipartFile file;
    MemberRequestDto requestDto;
    Member member;
    MemberResponseDto responseDto;
    MemberUpdateDto updateDto;

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
        responseDto = MemberResponseDto.builder()
                .seq(1L)
                .id("song")
                .name("송도언")
                .password(passwordEncoder.encode("1234"))
                .build();
        updateDto = MemberUpdateDto.builder()
                .name("이현규")
                .password(passwordEncoder.encode("5678"))
                .build();
    }

    @Test
    @DisplayName("정상적인 회원가입")
    void signUp() {
        when(memberRepository.save(any())).thenReturn(member);

        CommonResponseDto expect = memberService.signUp(requestDto, file);

        assertThat(expect.getResult()).isEqualTo("OK");
    }

    @Test
    @DisplayName("이미 존재하는 아이디")
    void AlreadyExistUsername() {
        when(memberRepository.existsById("song")).thenReturn(true);
        UserExistException exception = assertThrows(UserExistException.class,
                () -> memberService.signUp(requestDto, file));

        String message = exception.getErrorCode().getMessage();
        assertThat(message).isEqualTo("User Already Exist");
    }

    @Test
    @DisplayName("회원정보 조회")
    void detail() throws Exception{
        // given
        when(memberRepository.findBySeq(1L)).thenReturn(Optional.of(member));
        // when
        String expect = memberService.detailMember(1L).getName();

        // then
        assertThat(expect).isEqualTo(responseDto.getName());
    }


    @Test
    @DisplayName("회원 수정")
    void update() throws Exception {
        // given
        when(memberRepository.findBySeq(1L)).thenReturn(Optional.of(member));
        MemberResponseDto expect = MemberResponseDto.builder()
                .seq(1L)
                .id("song")
                .name("이현규")
                .password(passwordEncoder.encode("5678"))
                .build();

        // when
        MemberResponseDto result = memberService.updateMember(updateDto, 1L);

        // then
        assertThat(result.getName()).isEqualTo(expect.getName());
        assertThat(result.getPassword()).isEqualTo(expect.getPassword());
    }

    @Test
    @DisplayName("회원 삭제")
    void delete() throws Exception{
        // given
        when(memberRepository.findBySeq(1L)).thenReturn(Optional.of(member));
//        doNothing().when(memberRepository).delete(member);

        // when
        CommonResponseDto result = memberService.deleteMember(1L);

        // then
        assertThat(result.getResult()).isEqualTo("OK");
    }

}