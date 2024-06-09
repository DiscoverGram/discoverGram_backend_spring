package com.ssafy.enjoytrip.domain.follow.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FollowDto {
    private Long seq;
    private String name;
    private String userProfileImage;
    private Boolean isFollow;
}
