package com.ssafy.enjoytrip.domain.comment.dto;

import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentSeq;
    private String commentWriter;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
