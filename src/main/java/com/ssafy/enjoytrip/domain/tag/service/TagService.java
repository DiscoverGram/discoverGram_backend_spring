package com.ssafy.enjoytrip.domain.tag.service;

import com.ssafy.enjoytrip.domain.tag.domain.Tag;
import com.ssafy.enjoytrip.domain.tag.dto.TagRequestDto;
import com.ssafy.enjoytrip.domain.tag.dto.TagResponseDto;
import com.ssafy.enjoytrip.domain.tag.repository.TagRepository;
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