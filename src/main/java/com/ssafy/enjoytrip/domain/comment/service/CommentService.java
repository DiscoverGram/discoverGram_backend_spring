package com.ssafy.enjoytrip.domain.comment.service;

import com.ssafy.enjoytrip.domain.comment.domain.Comment;
import com.ssafy.enjoytrip.domain.comment.dto.CommentRequestDto;
import com.ssafy.enjoytrip.domain.comment.dto.CommentResponseDto;
import com.ssafy.enjoytrip.domain.comment.repository.CommentRepository;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import com.ssafy.enjoytrip.domain.post.domain.Post;
import com.ssafy.enjoytrip.domain.post.repository.PostRepository;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import com.ssafy.enjoytrip.global.error.CommonErrorCode;
import com.ssafy.enjoytrip.global.error.exception.NotFoundCommentException;
import com.ssafy.enjoytrip.global.error.exception.NotFoundMemberException;
import com.ssafy.enjoytrip.global.error.exception.NotFoundPostException;
import com.ssafy.enjoytrip.global.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommonResponseDto createComment(Long postSeq, CommentRequestDto commentRequestDto){
        Post post = postRepository.findById(postSeq).orElseThrow(() -> new NotFoundPostException(CommonErrorCode.NOT_FOUND_POST));
        Comment comment = Comment.builder()
                        .post(post)
                        .content(commentRequestDto.getContent())
                        .member(getMember())
                        .build();
        commentRepository.save(comment);
        return new CommonResponseDto("OK");
    }
    public List<CommentResponseDto> findByPostSeq(Long postSeq, Pageable pageable){
        Page<Comment> pages = commentRepository.findAllByPost_Seq(postSeq, pageable).orElseThrow(() -> new NotFoundCommentException(CommonErrorCode.NOT_FOUND_COMMENT));
        return pages.getContent().stream().map(Comment::CommentToDto).collect(Collectors.toList());
    }
    public CommentResponseDto updateComment(Long commentSeq, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(commentSeq).orElseThrow(() -> new NotFoundCommentException(CommonErrorCode.NOT_FOUND_COMMENT));
        comment.update(commentRequestDto);
        return Comment.CommentToDto(comment);
    }
    public CommonResponseDto delete(Long commentSeq){
        Comment comment = commentRepository.findById(commentSeq).orElseThrow(() -> new NotFoundCommentException(CommonErrorCode.NOT_FOUND_COMMENT));
        commentRepository.delete(comment);
        return new CommonResponseDto("OK");
    }
    public Member getMember(){
        String userName = AuthenticationUtil.authenticationGetUsername();
        return memberRepository.findByName(userName).orElseThrow(() -> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));
    }
}
