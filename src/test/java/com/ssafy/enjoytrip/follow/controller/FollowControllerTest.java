package com.ssafy.enjoytrip.follow.controller;//package com.ssafy.enjoytrip.follow.controller;

import com.ssafy.enjoytrip.domain.follow.controller.FollowController;
import com.ssafy.enjoytrip.domain.follow.dto.FollowDto;
import com.ssafy.enjoytrip.domain.follow.service.FollowService;
import com.ssafy.enjoytrip.domain.member.dto.MemberResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FollowController.class)
@ExtendWith(SpringExtension.class)
@WithMockUser
class FollowControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FollowService followService;

    private MemberResponseDto memberResponseDto;
    private FollowDto followDto;
    private List<FollowDto> followDtoList;
    private Long followMemberSeq;
    private Long memberSeq;
    @BeforeEach
    void init() {
        memberResponseDto = MemberResponseDto.builder()
                .seq(1L)
                .id("user1")
                .name("John Doe")
                .build();

        followDto = FollowDto.builder()
                .name("followUser")
                .userProfileImage("src")
                .build();

        followDtoList = Arrays.asList(followDto);

        followMemberSeq = 2L;
        memberSeq = 1L;
    }

    @Test
    @DisplayName("팔로우 추가")
    void follow() throws Exception {
        // given
        when(followService.follow(followMemberSeq, memberSeq)).thenReturn(true);

        // when, then
        mockMvc.perform(post("/follows/{followMemberSeq}/{memberSeq}", followMemberSeq, memberSeq)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("팔로우 삭제")
    void deleteFollowing() throws Exception {
        // given
        when(followService.deleteFollow(followMemberSeq, memberSeq)).thenReturn(true);

        // when, then
        mockMvc.perform(delete("/followings/{followMemberSeq}/{memberSeq}", followMemberSeq, memberSeq)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("팔로워 삭제")
    void deleteFollower() throws Exception {
        // given
        when(followService.deleteFollower(followMemberSeq, memberSeq)).thenReturn(true);

        // when, then
        mockMvc.perform(delete("/followers/{followedMemberSeq}/{memberSeq}", followMemberSeq, memberSeq)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("팔로잉 목록 조회")
//    void getFollowing() throws Exception {
//        // given
//        given(followService.getFollowing(memberSeq)).willReturn(followDtoList);
//
//        // when, then
//        mockMvc.perform(get("/followings/{memberSeq}", memberSeq)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("팔로워 목록 조회")
//    void getFollower() throws Exception {
//        // given
////        given(followService.getFollower(memberSeq)).willReturn(followDtoList);
//
//        // when, then
//        mockMvc.perform(get("/followers/{memberSeq}", memberSeq)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
}
