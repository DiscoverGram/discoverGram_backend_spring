package com.ssafy.enjoytrip.domain.post.dto;

import com.ssafy.enjoytrip.domain.comment.domain.Comment;
import com.ssafy.enjoytrip.domain.comment.dto.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostNewsfeedDto {
    private Long postSeq;
    private String content;
    private String writer;
    private Long writerSeq;
    private String writerProfileImage;
    private String placeName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long likes;
    private Boolean isLike;
    private List<CommentResponseDto> commentList;
    private List<String> imageList;
}
