package com.ssafy.enjoytrip.follow.service;

import com.ssafy.enjoytrip.error.exception.AlreadyFollowException;
import com.ssafy.enjoytrip.error.exception.SelfFollowException;
import com.ssafy.enjoytrip.follow.domain.Follow;
import com.ssafy.enjoytrip.follow.dto.FollowDto;
import com.ssafy.enjoytrip.follow.repository.FollowRepository;
import com.ssafy.enjoytrip.member.domain.Member;
import com.ssafy.enjoytrip.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {
    @Mock
    FollowRepository followRepository;
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    FollowService followService;

    private Member member;
    private Member followMember;
    private Follow follow;
    private FollowDto followDto;


    @BeforeEach
    public void init(){
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

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .seq(followMember.getSeq())
                .id(followMember.getId())
                .name(followMember.getName())
                .build();

        followDto = FollowDto.builder()
                .memberResponseDto(memberResponseDto)
                .build();
    }

    @Test
    @DisplayName("나 자신 팔로우")
    public void self_follow() throws Exception{
        //given
        long memberSeq = 1L;
        long followMemberSeq = 1L;
        //when

        //then
        Assertions.assertThrows(SelfFollowException.class, () -> followService.follow(followMemberSeq, memberSeq));
    }

    @Test
    @DisplayName("이미 팔로우한 멤버")
    public void already_follow() throws Exception{
        //given
        long memberSeq = 1L;
        long followMemberSeq = 1L;
        when(followRepository.existsByFollowMemberSeqAndMemberSeq(followMemberSeq, memberSeq)).thenReturn(true);
        //when

        //then
        Assertions.assertThrows(AlreadyFollowException.class, () -> followService.follow(followMemberSeq, memberSeq));
    }

    @Test
    @DisplayName("정상 팔로우")
    public void follow() {
        // given
        Long memberSeq = 1L;
        Long followMemberSeq = 2L;
        when(memberRepository.findBySeq(memberSeq)).thenReturn(Optional.of(member));
        when(memberRepository.findBySeq(followMemberSeq)).thenReturn(Optional.of(followMember));
        when(followRepository.save(any(Follow.class))).thenReturn(follow);

        // when
        boolean result = followService.follow(followMemberSeq, memberSeq);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("나 자신 팔로워 삭제")
    public void myself_deleteFollow() throws Exception{
        //given
        Long memberSeq = 1L;
        Long followMemberSeq = 1L;

        //when

        //then
        assertThrows(SelfFollowException.class, () -> followService.deleteFollow(followMemberSeq, memberSeq));
    }

    @Test
    @DisplayName("정상 팔로워 삭제")
    public void deleteFollow() {
        // given
        Long memberSeq = 1L;
        Long followMemberSeq = 2L;
        when(memberRepository.findBySeq(memberSeq)).thenReturn(Optional.of(member));
        when(memberRepository.findBySeq(followMemberSeq)).thenReturn(Optional.of(followMember));
        when(followRepository.existsByFollowMemberSeqAndMemberSeq(followMemberSeq, memberSeq)).thenReturn(true);

        // when
        boolean result = followService.deleteFollow(followMemberSeq, memberSeq);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("나 자신 팔로워 삭제")
    public void myself_deleteFollower() throws Exception{
        //given
        Long memberSeq = 1L;
        Long followedMemberSeq = 1L;

        //when

        //then
        assertThrows(SelfFollowException.class, () -> followService.deleteFollower(followedMemberSeq, memberSeq));
    }

    @Test
    public void deleteFollower_ShouldReturnTrue() {
        // given
        Long memberSeq = 1L;
        Long followedMemberSeq = 2L;
        when(memberRepository.findBySeq(memberSeq)).thenReturn(Optional.of(member));
        when(memberRepository.findBySeq(followedMemberSeq)).thenReturn(Optional.of(followMember));
        when(followRepository.existsByFollowMemberSeqAndMemberSeq(followedMemberSeq, memberSeq)).thenReturn(true);

        // when
        boolean result = followService.deleteFollower(followedMemberSeq, memberSeq);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("팔로잉 리스트 출력")
    public void getFollowing() throws Exception{
        // given
        Long memberSeq = 1L;
        List<Long> followingList = Arrays.asList(2L, 3L);
        when(followRepository.findFollowingList(memberSeq)).thenReturn(followingList);

        List<FollowDto> followingDtoList = Arrays.asList(followDto);
        when(followRepository.findFollowingDtoList(followingList)).thenReturn(followingDtoList);

        // when
        List<FollowDto> result = followService.getFollowing(memberSeq);

        // then
        assertThat(result).isEqualTo(followingDtoList);
    }

    @Test
    @DisplayName("팔로워 리스트 출력")
    public void getFollower() throws Exception{
        // given
        Long memberSeq = 1L;
        List<Long> followerList = Arrays.asList(2L, 3L);
        List<FollowDto> followerDtoList = Arrays.asList(followDto);
        when(followRepository.findFollowerList(memberSeq)).thenReturn(followerList);
        when(followRepository.findFollowerDtoList(followerList)).thenReturn(followerDtoList);

        // when
        List<FollowDto> result = followService.getFollower(memberSeq);

        // then
        assertThat(result).isEqualTo(followerDtoList);
    }
}