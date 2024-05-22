package com.ssafy.enjoytrip.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowDto {
    private String name;
    private String userProfileImage;
    private Boolean isFollow;
}
