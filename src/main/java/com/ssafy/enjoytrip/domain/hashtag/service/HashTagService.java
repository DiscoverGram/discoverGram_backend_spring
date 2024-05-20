package com.ssafy.enjoytrip.domain.hashtag.service;

import com.ssafy.enjoytrip.domain.hashtag.repository.HashTagRepository;
import com.ssafy.enjoytrip.domain.post.dto.PostResponseDto;
import com.ssafy.enjoytrip.domain.post.repository.PostRepository;
import com.ssafy.enjoytrip.domain.tag.domain.Tag;
import com.ssafy.enjoytrip.domain.tag.repository.TagRepository;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import com.ssafy.enjoytrip.global.error.CommonErrorCode;
import com.ssafy.enjoytrip.global.error.exception.NotFoundPostException;
import com.ssafy.enjoytrip.domain.hashtag.domain.HashTag;
import com.ssafy.enjoytrip.domain.hashtag.dto.HashTagRequestDto;
import com.ssafy.enjoytrip.domain.post.domain.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HashTagService {
    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;
    private final TagRepository tagRepository;

    public CommonResponseDto createHashTag(Long postSeq, HashTagRequestDto hashTagRequestDto) {
        Post post = postRepository.findById(postSeq).orElseThrow(() -> new NotFoundPostException(CommonErrorCode.NOT_FOUND_POST));
        for (Long tagSeq : hashTagRequestDto.getHashTags()) {
            Tag tag = tagRepository.findById(tagSeq).orElse(null);
            if (tag == null) continue;

            HashTag hashTag = HashTag.builder()
                                        .pk(new HashTag.Pk(postSeq, tagSeq))
                                        .tag(tag)
                                        .post(post)
                                        .build();
            hashTagRepository.save(hashTag);
        }
        return new CommonResponseDto("OK");
    }

    @GetMapping
    public List<PostResponseDto> findPostByHashtag(Long tagSeq, Pageable pageable) {
        Page<Post> page = hashTagRepository.findPostsByTagSeq(tagSeq, pageable).orElseThrow(() -> new NotFoundPostException(CommonErrorCode.NOT_FOUND_POST));
        return page.getContent().stream().map(Post::PostToDto)
                .collect(Collectors.toList());
    }
}
