package com.ssafy.enjoytrip.domain.hashtag.controller;

import com.ssafy.enjoytrip.domain.hashtag.service.HashTagService;
import com.ssafy.enjoytrip.domain.post.dto.PostResponseDto;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import com.ssafy.enjoytrip.domain.hashtag.dto.HashTagRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hashtags")
public class HashTagController {
    private final HashTagService hashTagService;

    @PostMapping("/posts/{postId}")
    public ResponseEntity<CommonResponseDto> createHashTag(@PathVariable Long postId, @RequestBody HashTagRequestDto hashTagRequestDto) {
        return ResponseEntity.ok(hashTagService.createHashTag(postId, hashTagRequestDto));
    }

    @GetMapping("/{hashtagSeq}")
    public List<PostResponseDto> getHashTags(@PathVariable Long hashtagSeq, @PageableDefault Pageable pageable) {
        return hashTagService.findPostByHashtag(hashtagSeq, pageable);
    }
}
