package com.ssafy.enjoytrip.tag.service;

import com.ssafy.enjoytrip.common.CommonResponseDto;
import com.ssafy.enjoytrip.tag.domain.Tag;
import com.ssafy.enjoytrip.tag.dto.TagRequestDto;
import com.ssafy.enjoytrip.tag.dto.TagResponseDto;
import com.ssafy.enjoytrip.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    public TagResponseDto findById(Long tagSeq) {
        Tag tag = tagRepository.findById(tagSeq).orElseThrow();
        return new TagResponseDto(tag.getSeq());
    }

    public List<TagResponseDto> createTag(TagRequestDto tagRequestDto) {
        List<TagResponseDto> responseList = new ArrayList<>();
        for(String tagName : tagRequestDto.getTags()){
            if(tagRepository.existsTagByTag(tagName)) continue;
            Tag tag = Tag.builder().tag(tagName).build();
            responseList.add(new TagResponseDto(tagRepository.save(tag).getSeq()));
        }
        return responseList;
    }
}