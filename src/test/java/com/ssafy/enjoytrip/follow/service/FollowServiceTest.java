package com.ssafy.enjoytrip.follow.service;//package com.ssafy.enjoytrip.follow.service;

import com.ssafy.enjoytrip.domain.follow.domain.Follow;
import com.ssafy.enjoytrip.domain.follow.dto.FollowDto;
import com.ssafy.enjoytrip.domain.follow.repository.FollowRepository;
import com.ssafy.enjoytrip.domain.follow.service.FollowService;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import com.ssafy.enjoytrip.global.error.exception.AlreadyFollowException;
import com.ssafy.enjoytrip.global.error.exception.SelfFollowException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private Member followedMember;
    private Member followMember;
    private Follow follow;
    private FollowDto followDto;
    private FollowDto followerDto;


    @BeforeEach
    public void init(){

        followMember = Member.builder()
                .seq(2L)
                .id("user2")
                .name("following")
                .password("password")
                .build();

        followedMember = Member.builder()
                .seq(1L)
                .id("user1")
                .name("follower")
                .password("password")
                .build();

        follow = Follow.builder()
                .followMember(followMember)
                .followedMember(followedMember)
                .build();

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .seq(followMember.getSeq())
                .id(followMember.getId())
                .name(followMember.getName())
                .build();

        followDto = FollowDto.builder()
                .name("following")
                .userProfileImage("src")
                .build();

        followerDto = FollowDto.builder()
                .name("follower")
                .userProfileImage("src")
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
        long followMemberSeq = 2L;
        when(followRepository.existsByFollowMember_SeqAndFollowedMember_Seq(followMemberSeq, memberSeq)).thenReturn(true);
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
        when(memberRepository.findBySeq(memberSeq)).thenReturn(Optional.of(followedMember));
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
        when(memberRepository.findBySeq(memberSeq)).thenReturn(Optional.of(followedMember));
        when(memberRepository.findBySeq(followMemberSeq)).thenReturn(Optional.of(followMember));

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
    @DisplayName("팔로워 삭제")
    public void deleteFollower() {
        // given
        Long memberSeq = 1L;
        Long followedMemberSeq = 2L;
        when(memberRepository.findBySeq(memberSeq)).thenReturn(Optional.of(followedMember));
        when(memberRepository.findBySeq(followedMemberSeq)).thenReturn(Optional.of(followMember));

        // when
        boolean result = followService.deleteFollower(followedMemberSeq, memberSeq);

        // then
        assertThat(result).isTrue();
    }
//
//    @Test
//    @DisplayName("팔로잉 리스트 출력")
//    public void getFollowing() throws Exception{
//        // given
//        Long memberSeq = 1L;
//
//        List<FollowDto> followDtoList = Collections.singletonList(followDto);
//        List<Follow> followList = Collections.singletonList(follow);
//        when(followRepository.findFollowedList(memberSeq)).thenReturn(followList);
//
//        // when
//        List<FollowDto> result = followService.getFollowing(memberSeq);
//
//        // then
//        assertThat(result.get(0).getName()).isEqualTo(followDtoList.get(0).getName());
//    }

    @Test
    @DisplayName("팔로워 리스트 출력")
    public void getFollower() throws Exception{
        // given
        Long memberSeq = 1L;
        List<FollowDto> followerDtoList = Arrays.asList(followerDto);
        List<Follow> followList = Collections.singletonList(follow);
        when(followRepository.findFollowList(memberSeq)).thenReturn(followList);

        // when
//        List<FollowDto> result = followService.getFollower(memberSeq);

        // then
//        assertThat(result.get(0).getName()).isEqualTo(followerDtoList.get(0).getName());
    }
}