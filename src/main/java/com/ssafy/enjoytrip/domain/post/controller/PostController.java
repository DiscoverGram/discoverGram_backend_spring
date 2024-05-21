package com.ssafy.enjoytrip.domain.post.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.ssafy.enjoytrip.domain.post.dto.PostNewsfeedDto;
import com.ssafy.enjoytrip.domain.post.dto.PostRequestDto;
import com.ssafy.enjoytrip.domain.post.dto.PostResponseDto;
import com.ssafy.enjoytrip.domain.post.service.PostService;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @PostMapping
    public ResponseEntity<CommonResponseDto> create(@RequestBody PostRequestDto postRequestDto, @RequestPart List<MultipartFile> files){
        return ResponseEntity.ok(postService.create(postRequestDto, files));
    }
    @GetMapping("/feed/{memberSeq}")
    public ResponseEntity<List<PostResponseDto>> getFeed(@PathVariable Long memberSeq, @PageableDefault Pageable pageable){
        return ResponseEntity.ok(postService.getFeed(memberSeq, pageable));
    }
    @GetMapping("/newsfeed/{memberSeq}")
    public ResponseEntity<List<PostNewsfeedDto>> getNewsFeed(@PathVariable Long memberSeq, @PageableDefault Pageable pageable){
        return ResponseEntity.ok(postService.getNewsFeed(memberSeq, pageable));
    }
    @PutMapping("/{postSeq}")
    public ResponseEntity<CommonResponseDto> updatePost(@PathVariable Long postSeq, @RequestBody PostRequestDto postRequestDto, @RequestPart List<MultipartFile> files){
        return ResponseEntity.ok(postService.update(postSeq, postRequestDto, files));
    }
}
