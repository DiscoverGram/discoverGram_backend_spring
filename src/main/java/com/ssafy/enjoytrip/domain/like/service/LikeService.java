package com.ssafy.enjoytrip.domain.like.service;

import com.ssafy.enjoytrip.domain.like.domain.Like;
import com.ssafy.enjoytrip.domain.like.repository.LikeRepository;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import com.ssafy.enjoytrip.domain.post.repository.PostRepository;
import com.ssafy.enjoytrip.domain.post.domain.Post;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import com.ssafy.enjoytrip.global.error.CommonErrorCode;
import com.ssafy.enjoytrip.global.error.exception.NotFoundLikeException;
import com.ssafy.enjoytrip.global.error.exception.NotFoundMemberException;
import com.ssafy.enjoytrip.global.error.exception.NotFoundPostException;
import com.ssafy.enjoytrip.global.util.AuthenticationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    public CommonResponseDto likePost(Long postSeq, Long memberSeq){
        Post post = postRepository.findById(postSeq).orElseThrow(() -> new NotFoundPostException(CommonErrorCode.NOT_FOUND_POST));
        Member member = getMember();
        Like like = Like.builder()
                .pk(new Like.Pk(postSeq, memberSeq))
                .post(post)
                .member(member)
                .build();
        likeRepository.save(like);
        return new CommonResponseDto("OK");
    }
    public CommonResponseDto deletePost(Long postSeq, Long memberSeq){
        Like like = likeRepository.findById(new Like.Pk(postSeq, memberSeq)).orElseThrow(() -> new NotFoundLikeException(CommonErrorCode.NOT_FOUND_LIKE));
        likeRepository.delete(like);
        return new CommonResponseDto("OK");
    }

    public Member getMember(){
        String userName = AuthenticationUtil.authenticationGetUsername();
        return memberRepository.findByName(userName).orElseThrow(() -> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));
    }
}
