package com.ssafy.enjoytrip.domain.comment.controller;

import com.ssafy.enjoytrip.domain.comment.dto.CommentRequestDto;
import com.ssafy.enjoytrip.domain.comment.dto.CommentResponseDto;
import com.ssafy.enjoytrip.domain.comment.service.CommentService;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class CommentController {


    private final CommentService commentService;

    @PostMapping("/post/{postSeq}/comments")
    public ResponseEntity<CommonResponseDto> createComment(@PathVariable Long postSeq, @RequestBody CommentRequestDto commentRequestDto){
        return ResponseEntity.ok(commentService.createComment(postSeq, commentRequestDto));
    }
    @GetMapping("/post/{postSeq}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentList(@PathVariable Long postSeq, @PageableDefault Pageable pageable){
        return ResponseEntity.ok(commentService.findByPostSeq(postSeq, pageable));
    }
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto){
        return ResponseEntity.ok(commentService.updateComment(commentId, commentRequestDto));
    }
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommonResponseDto> deleteComment(@PathVariable Long commentId){
        return ResponseEntity.ok(commentService.delete(commentId));
    }
}
