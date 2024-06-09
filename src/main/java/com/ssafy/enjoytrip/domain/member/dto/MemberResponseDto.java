package com.ssafy.enjoytrip.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Long seq;
    private String id;
    private String name;
    private String userProfileImage;
    private Long followingNumber;
    private Long followerNumber;
}
