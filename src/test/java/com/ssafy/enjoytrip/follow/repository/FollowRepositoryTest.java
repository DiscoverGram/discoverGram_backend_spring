package com.ssafy.enjoytrip.follow.repository;

import com.ssafy.enjoytrip.follow.domain.Follow;
import com.ssafy.enjoytrip.follow.dto.FollowDto;
import com.ssafy.enjoytrip.member.domain.Member;
import com.ssafy.enjoytrip.member.dto.MemberResponseDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class FollowRepositoryTest {

    @Mock
    private FollowRepository followRepository;

    private Member member;
    private Member followMember;
    private Follow follow;
    private FollowDto followDto;

    private Long followMemberSeq = 2L;
    private Long memberSeq = 1L;

    @BeforeEach
    void init() {
        member = Member.builder()
                .seq(1L)
                .id("user1")
                .name("John Doe")
                .password("password")
                .build();

        followMember = Member.builder()
                .seq(2L)
                .id("user2")
                .name("Jane Smith")
                .password("password")
                .build();

        follow = Follow.builder()
                .followMember(followMember)
                .followedMember(member)
                .build();

        followRepository.save(follow);

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .seq(followMember.getSeq())
                .id(followMember.getId())
                .name(followMember.getName())
                .build();

        followDto = FollowDto.builder()
                .memberResponseDto(memberResponseDto)
                .build();

        followMemberSeq = 2L;
        memberSeq = 1L;
    }

    @Test
    @DisplayName("팔로잉 체크")
    void existsByFollowMemberSeqAndMemberSeq() {
        // given

        // when
        boolean exists = followRepository.existsByFollowMemberSeqAndMemberSeq(followMemberSeq, memberSeq);

        // then
        assertThat(exists).isTrue();
    }


    @Test
    @DisplayName("팔로잉 seq 출력")
    void findFollowingList() {
        // given
        List<Long> expect = Arrays.asList(followMemberSeq);

        // when
        List<Long> actualFollowingList = followRepository.findFollowingList(memberSeq);

        // then
        assertThat(actualFollowingList).isEqualTo(expect);
    }

    @Test
    @DisplayName("팔로잉Dto 출력")
    void findFollowingDtoList() {
        // given
        List<Long> followedMemberSeqList = Arrays.asList(followMemberSeq);
        List<FollowDto> expect = Arrays.asList(followDto);

        // when
        List<FollowDto> actualFollowingDtoList = followRepository.findFollowingDtoList(followedMemberSeqList);

        // then
        assertThat(actualFollowingDtoList).isEqualTo(expect);
    }

    @Test
    @DisplayName("팔로워 seq 출력")
    void findFollowerList() {
        // given
        List<Long> expect = Arrays.asList(memberSeq);

        // when
        List<Long> actualFollowerList = followRepository.findFollowerList(followMemberSeq);

        // then
        assertThat(actualFollowerList).isEqualTo(expect);
    }

    @Test
    @DisplayName("팔로워Dto 출력")
    void findFollowerDtoList() {
        // given
        List<Long> followMemberSeqList = Arrays.asList(memberSeq);
        List<FollowDto> expect = Arrays.asList(followDto);

        // when
        List<FollowDto> actualFollowerDtoList = followRepository.findFollowerDtoList(followMemberSeqList);

        // then
        assertThat(actualFollowerDtoList).isEqualTo(expect);
    }


    @Test
    @DisplayName("팔로잉 숫자세기")
    void countByFollowMemberSeq() {
        // given
        int expect = 1;

        // when
        int actualCount = followRepository.countByFollowMemberSeq(memberSeq);

        // then
        assertThat(actualCount).isEqualTo(expect);
    }

    @Test
    @DisplayName("팔로워 숫자세기")
    void testCountByFollowedMemberSeq() {
        // given
        int expect = 1;

        // when
        int actualCount = followRepository.countByFollowedMemberSeq(followMemberSeq);

        // then
        assertThat(actualCount).isEqualTo(expect);
    }
}