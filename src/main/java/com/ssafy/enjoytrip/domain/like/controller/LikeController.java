package com.ssafy.enjoytrip.domain.like.controller;

import com.ssafy.enjoytrip.domain.like.service.LikeService;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    @PostMapping("/post/{postSeq}/like")
    public ResponseEntity<CommonResponseDto> likePost(@PathVariable Long postSeq) {
        return ResponseEntity.ok(likeService.likePost(postSeq, 0L));
    }

    @DeleteMapping("/post/{postSeq}/like")
    public ResponseEntity<CommonResponseDto> deletePost(@PathVariable Long postSeq) {
        return ResponseEntity.ok(likeService.deletePost(postSeq, 0L));
    }
}
