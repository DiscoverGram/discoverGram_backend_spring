package com.ssafy.enjoytrip.tag.service;

import com.ssafy.enjoytrip.common.CommonResponseDto;
import com.ssafy.enjoytrip.tag.domain.Tag;
import com.ssafy.enjoytrip.tag.dto.TagRequestDto;
import com.ssafy.enjoytrip.tag.dto.TagResponseDto;
import com.ssafy.enjoytrip.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    public TagResponseDto findById(Long tagSeq) {
        Tag tag = tagRepository.findById(tagSeq).orElseThrow();
        return new TagResponseDto(tag.getTag());
    }
    public CommonResponseDto createTag(TagRequestDto tagRequestDto) {
        Tag tag = Tag.builder().tag(tagRequestDto.getTag()).build();
        tagRepository.save(tag);
        return new CommonResponseDto("OK");
    }
}