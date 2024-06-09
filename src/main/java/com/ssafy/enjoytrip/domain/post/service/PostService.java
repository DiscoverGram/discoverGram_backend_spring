package com.ssafy.enjoytrip.domain.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.enjoytrip.domain.comment.domain.Comment;
import com.ssafy.enjoytrip.domain.comment.repository.CommentRepository;
import com.ssafy.enjoytrip.domain.follow.repository.FollowRepository;
import com.ssafy.enjoytrip.domain.hashtag.domain.HashTag;
import com.ssafy.enjoytrip.domain.hashtag.repository.HashTagRepository;
import com.ssafy.enjoytrip.domain.hashtag.service.HashTagService;
import com.ssafy.enjoytrip.domain.image.domain.Image;
import com.ssafy.enjoytrip.domain.image.repository.ImageRepository;
import com.ssafy.enjoytrip.domain.like.domain.Like;
import com.ssafy.enjoytrip.domain.like.repository.LikeRepository;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import com.ssafy.enjoytrip.domain.post.dto.PostNewsfeedDto;
import com.ssafy.enjoytrip.domain.post.dto.PostRequestDto;
import com.ssafy.enjoytrip.domain.post.dto.PostResponseDto;
import com.ssafy.enjoytrip.domain.post.repository.PostRepository;
import com.ssafy.enjoytrip.domain.tag.domain.Tag;
import com.ssafy.enjoytrip.domain.tag.repository.TagRepository;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import com.ssafy.enjoytrip.global.error.CommonErrorCode;
import com.ssafy.enjoytrip.global.error.exception.NotFoundMemberException;
import com.ssafy.enjoytrip.global.error.exception.NotFoundPostException;
import com.ssafy.enjoytrip.domain.place.domain.Place;
import com.ssafy.enjoytrip.domain.place.repository.PlaceRepository;
import com.ssafy.enjoytrip.domain.post.domain.Post;
import com.ssafy.enjoytrip.global.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final HashTagRepository hashTagRepository;

    public CommonResponseDto create(PostRequestDto postRequestDto, List<MultipartFile> files){
        Member member = getMember();
        Post post = Post.builder()
                .content(postRequestDto.getContent())
                .writer(member)
                .place(Place.builder().placeName("야호").seq(1L).latitude(12.142).longitude(15.2423).build())
                .build();
        Long postSeq = 0L;
        String tags = postRequestDto.getTag();

        List<String> rollbackKeyList = new ArrayList<>();
//        System.out.println("post = " + post.getThumbnailImage());
        try {
            for (MultipartFile file : files) {
                UUID uuid = UUID.randomUUID();
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                String key = uuid + "." + extension;
                if(Objects.isNull(post.getThumbnailImage())){
                    post.addImage(key);
                    postSeq = postRepository.save(post).getSeq();
                    String[] tagNames = tags.split(",");
                    for (String tagName : tagNames) {
                        Tag tag = Tag.builder().tag(tagName).build();
                        Long tagSeq = tagRepository.save(tag).getSeq();
                        hashTagRepository.save(HashTag.builder().pk(new HashTag.Pk(postSeq, tagSeq))
                                .post(post)
                                .tag(tag)
                                .build());
                    }
                }
                ObjectMetadata metadata= new ObjectMetadata();
                metadata.setContentType(file.getContentType());
                metadata.setContentLength(file.getSize());
                Image image = Image.builder()
                        .pk(new Image.Pk(postSeq,key))
                        .post(post)
                        .extension(extension)
                        .build();


                amazonS3Client.putObject(bucket, key, file.getInputStream(),metadata);
                rollbackKeyList.add(key);
                imageRepository.save(image);
            }
            return new CommonResponseDto("OK");
        } catch (IOException e) {
            for (String key : rollbackKeyList) {
                amazonS3Client.deleteObject(bucket, key);
            }
            e.printStackTrace();
            return new CommonResponseDto("파일 업로드중 오류가 발생 하였습니다.");
        }
    }

    public PostNewsfeedDto getPostDetail(Long postSeq){
        Post post = postRepository.findById(postSeq).orElseThrow(() -> new NotFoundPostException(CommonErrorCode.NOT_FOUND_POST));
        List<Comment> list = commentRepository.findTop3ByPost_Seq(postSeq).orElseThrow();

        Member member = getMember();
        return PostNewsfeedDto.builder()
                .content(post.getContent())
                        .writer(post.getWriter().getName())
                        .placeName(post.getPlace().getPlaceName())
                        .writerSeq(post.getWriter().getSeq())
                        .writerProfileImage(post.getWriter().getUserProfileImage())
                        .imageList(imageRepository.findByPk_PostSeq(post.getSeq())
                                .orElse(new ArrayList<>())
                                .stream()
                                .map(image -> image.getPk().getImageUuid())
                                .collect(Collectors.toList()))
                        .commentList(commentRepository.findTop3ByPost_Seq(post.getSeq())
                                .orElse(new ArrayList<>())
                                .stream()
                                .map(Comment::CommentToDto)
                                .toList())
                        .createdDate(post.getCreatedDate())
                        .updatedDate(post.getUpdatedDate())
                        .likes(likeRepository.countByPk(new Like.Pk(member.getSeq(), post.getSeq())))
                        .isLike(likeRepository.existsById(new Like.Pk(member.getSeq(), post.getSeq())))
                        .build();
    }
    public List<PostNewsfeedDto> getNewsFeed(Long memberSeq, Pageable pageable){
        Page<Post> posts = postRepository.findByMemberSeq(memberSeq, pageable).orElseThrow(() -> new NotFoundPostException(CommonErrorCode.NOT_FOUND_POST));
        List<Comment> list = commentRepository.findTop3ByPost_Seq(1L).orElseThrow();
        for (Comment comment : list) {
            log.info(comment.getContent());
            log.info(comment.getCommentSeq().toString());
            log.info(comment.getMember().getName());
        }
        log.info("사이즈 !!!! : {}",list.size());
        return posts.getContent().stream().map(post -> PostNewsfeedDto.builder()
                        .postSeq(post.getSeq())
                        .content(post.getContent())
                        .writer(post.getWriter().getName())
                        .writerSeq(post.getWriter().getSeq())
                        .writerProfileImage(post.getWriter().getUserProfileImage())
                        .placeName(post.getPlace().getPlaceName())
                        .imageList(imageRepository.findByPk_PostSeq(post.getSeq())
                                .orElse(new ArrayList<>())
                                .stream()
                                .map(image -> image.getPk().getImageUuid())
                                .toList())
                        .commentList(commentRepository.findTop3ByPost_Seq(post.getSeq())
                                .orElse(new ArrayList<>())
                                .stream()
                                .map(Comment::CommentToDto)
                                .toList())
                        .createdDate(post.getCreatedDate())
                        .updatedDate(post.getUpdatedDate())
                        .likes(likeRepository.countByPk(new Like.Pk(memberSeq, post.getSeq())))
                        .isLike(likeRepository.existsById(new Like.Pk(memberSeq, post.getSeq())))
                        .build())
                .toList();
    }
    public List<PostResponseDto> getFeed(Long memberSeq, Pageable pageable){
        Page<Post> posts = postRepository.findByWriter_Seq(memberSeq, pageable).orElseThrow(() -> new NotFoundPostException(CommonErrorCode.NOT_FOUND_POST));

        return posts.getContent().stream().map(post -> PostResponseDto.builder()
                        .postSeq(post.getSeq())
                        .thumbnailImage(post.getThumbnailImage())
                        .build())
                .collect(Collectors.toList());
    }
    public CommonResponseDto update(Long postSeq, PostRequestDto postRequestDto){
        Post post = postRepository.findById(postSeq).orElseThrow(() -> new NotFoundPostException(CommonErrorCode.NOT_FOUND_POST));
        Place place = placeRepository.findByPlaceName(postRequestDto.getLocation()).orElseThrow();
        post.update(postRequestDto.getContent(), place);
        return new CommonResponseDto("OK");
    }
    public Member getMember(){
//        String userName = AuthenticationUtil.authenticationGetUsername();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.isAuthenticated());
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getName());
        return memberRepository.findByName(authentication.getName()).orElseThrow(() -> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));
    }
}
