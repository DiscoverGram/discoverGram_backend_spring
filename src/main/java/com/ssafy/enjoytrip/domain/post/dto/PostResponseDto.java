package com.ssafy.enjoytrip.domain.post.dto;

import com.ssafy.enjoytrip.domain.comment.domain.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long postSeq;
    private String thumbnailImage;
}
