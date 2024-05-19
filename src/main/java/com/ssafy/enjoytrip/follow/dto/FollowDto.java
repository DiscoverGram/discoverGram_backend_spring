package com.ssafy.enjoytrip.follow.dto;

import com.ssafy.enjoytrip.member.dto.MemberResponseDto;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowDto {
    MemberResponseDto memberResponseDto;
}
