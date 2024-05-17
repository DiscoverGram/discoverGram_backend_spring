package com.ssafy.enjoytrip.tag.controller;

import com.ssafy.enjoytrip.common.CommonResponseDto;
import com.ssafy.enjoytrip.tag.dto.TagRequestDto;
import com.ssafy.enjoytrip.tag.dto.TagResponseDto;
import com.ssafy.enjoytrip.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<List<TagResponseDto>> createTag(@RequestBody TagRequestDto tagRequestDto) {
        return ResponseEntity.ok(tagService.createTag(tagRequestDto));
    }
    @GetMapping("/{tagId}")
    public ResponseEntity<TagResponseDto> getTags(@PathVariable Long tagId) {
        return ResponseEntity.ok(tagService.findById(tagId));
    }
}
