package com.ssafy.enjoytrip.hashtag.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HashTagRequestDto {
    private List<Long> HashTags;
}
