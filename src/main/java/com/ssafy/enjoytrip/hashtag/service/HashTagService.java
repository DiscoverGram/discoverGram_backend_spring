package com.ssafy.enjoytrip.hashtag.service;

import com.ssafy.enjoytrip.common.CommonResponseDto;
import com.ssafy.enjoytrip.error.CommonErrorCode;
import com.ssafy.enjoytrip.error.exception.NotFoundPostException;
import com.ssafy.enjoytrip.hashtag.domain.HashTag;
import com.ssafy.enjoytrip.hashtag.dto.HashTagRequestDto;
import com.ssafy.enjoytrip.hashtag.repository.HashTagRepository;
import com.ssafy.enjoytrip.post.domain.Post;
import com.ssafy.enjoytrip.post.dto.PostResponseDto;
import com.ssafy.enjoytrip.post.repository.PostRepository;
import com.ssafy.enjoytrip.tag.domain.Tag;
import com.ssafy.enjoytrip.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<PostResponseDto> findByHashtag(Long tagSeq,Pageable pageable) {
        Optional<Page<Post>> page = hashTagRepository.findPostsByTagSeq(tagSeq, pageable);
        if(page.isEmpty()) return new ArrayList<>();
        List<Post> PostList = page.get().getContent();
//        return PostList.stream().map(Post -> new PostResponseDto(tag))
        return null;
    }
}
