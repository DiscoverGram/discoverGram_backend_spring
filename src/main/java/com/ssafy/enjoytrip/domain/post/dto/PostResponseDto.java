package com.ssafy.enjoytrip.domain.post.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long postSeq;
    private String content;
    private String thumbnailImage;
    private String writer;
    private String placeName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
