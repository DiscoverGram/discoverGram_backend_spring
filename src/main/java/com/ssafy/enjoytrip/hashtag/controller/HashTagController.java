package com.ssafy.enjoytrip.hashtag.controller;

import com.ssafy.enjoytrip.common.CommonResponseDto;
import com.ssafy.enjoytrip.hashtag.domain.HashTag;
import com.ssafy.enjoytrip.hashtag.dto.HashTagRequestDto;
import com.ssafy.enjoytrip.hashtag.service.HashTagService;
import com.ssafy.enjoytrip.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hashtags")
public class HashTagController {
    private final HashTagService hashTagService;

    @PostMapping("/posts/{post}")
    public ResponseEntity<CommonResponseDto> createHashTag(@PathVariable Long postId, @RequestBody HashTagRequestDto hashTagRequestDto) {
        return ResponseEntity.ok(hashTagService.createHashTag(postId, hashTagRequestDto));
    }

    @GetMapping("/{hashtagSeq}")
    public List<HashTag> getHashTags(@PathVariable Long hashtagSeq, @PageableDefault Pageable pageable) {
        return hashTagService.findByHashtag(hashtagSeq, pageable);
    }
}
