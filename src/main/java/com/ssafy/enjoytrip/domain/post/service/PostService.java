package com.ssafy.enjoytrip.domain.post.service;

import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.post.dto.PostRequestDto;
import com.ssafy.enjoytrip.domain.post.dto.PostResponseDto;
import com.ssafy.enjoytrip.domain.post.repository.PostRepository;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import com.ssafy.enjoytrip.global.error.CommonErrorCode;
import com.ssafy.enjoytrip.global.error.exception.NotFoundMemberException;
import com.ssafy.enjoytrip.global.error.exception.NotFoundPostException;
import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import com.ssafy.enjoytrip.domain.place.domain.Place;
import com.ssafy.enjoytrip.domain.place.repository.PlaceRepository;
import com.ssafy.enjoytrip.domain.post.domain.Post;
import com.ssafy.enjoytrip.global.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;

    public CommonResponseDto create(PostRequestDto postRequestDto){
        Member member = getMember();
        Place place = placeRepository.findByPlaceName(postRequestDto.getPlaceName()).orElseThrow();
        Post post = Post.builder()
                .content(postRequestDto.getContent())
                .writer(member)
                .place(place)
                .build();
        postRepository.save(post);
        return new CommonResponseDto("OK");
    }
    public List<PostResponseDto> getNewsFeed(Pageable pageable, Long memberSeq){
        Page<Post> posts = postRepository.findByNewsfeed(memberSeq, pageable).orElseThrow(() -> new NotFoundPostException(CommonErrorCode.NOT_FOUND_POST));
        return posts.getContent().stream().map(post -> PostResponseDto.builder()
                        .content(post.getContent())
                        .writer(post.getWriter().getName())
                        .placeName(post.getPlace().getPlaceName())
                        .build())
                .collect(Collectors.toList());
    }
    public Member getMember(){
        String userName = AuthenticationUtil.authenticationGetUsername();
        return memberRepository.findByName(userName).orElseThrow(() -> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));
    }
}
