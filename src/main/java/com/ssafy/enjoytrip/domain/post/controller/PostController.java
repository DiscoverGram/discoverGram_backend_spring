package com.ssafy.enjoytrip.domain.post.controller;

import com.ssafy.enjoytrip.domain.post.dto.PostRequestDto;
import com.ssafy.enjoytrip.domain.post.dto.PostResponseDto;
import com.ssafy.enjoytrip.domain.post.service.PostService;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<CommonResponseDto> create(@RequestBody PostRequestDto postRequestDto){
        return ResponseEntity.ok(postService.create(postRequestDto));
    }
    @GetMapping("/newsfeed/{memberSeq}")
    public ResponseEntity<List<PostResponseDto>> getNewsFeed(@PageableDefault Pageable pageable, Long memberSeq){
        return ResponseEntity.ok(postService.getNewsFeed(pageable, memberSeq));
    }
}
