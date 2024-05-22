package com.ssafy.enjoytrip.domain.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.enjoytrip.domain.comment.domain.Comment;
import com.ssafy.enjoytrip.domain.follow.repository.FollowRepository;
import com.ssafy.enjoytrip.domain.image.domain.Image;
import com.ssafy.enjoytrip.domain.image.repository.ImageRepository;
import com.ssafy.enjoytrip.domain.like.domain.Like;
import com.ssafy.enjoytrip.domain.like.repository.LikeRepository;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.post.dto.PostNewsfeedDto;
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
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
public class PostService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;
    private final LikeRepository likeRepository;

    public CommonResponseDto create(PostRequestDto postRequestDto, List<MultipartFile> files){
        Member member = getMember();
        Place place = placeRepository.findByPlaceName(postRequestDto.getPlaceName()).orElseThrow();
        Post post = Post.builder()
                .content(postRequestDto.getContent())
                .writer(member)
                .place(place)
                .build();
        Long postSeq = 0L;
        List<String> rollbackKeyList = new ArrayList<>();
        try {
            for (MultipartFile file : files) {

                UUID uuid = UUID.randomUUID();
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                String key = uuid + "." + extension;
                if(Objects.isNull(post.getThumbnailImage())){
                    post.addImage(key);
                    postSeq = postRepository.save(post).getSeq();
                }
                ObjectMetadata metadata= new ObjectMetadata();
                metadata.setContentType(file.getContentType());
                metadata.setContentLength(file.getSize());
                Image image = Image.builder()
                        .pk(new Image.Pk(postSeq,uuid.toString()))
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
        Member member = getMember();
        return PostNewsfeedDto.builder()
                .content(post.getContent())
                        .writer(post.getWriter().getName())
                        .placeName(post.getPlace().getPlaceName())
                        .imageList(imageRepository.findByPk_PostSeq(post.getSeq())
                                .orElse(new ArrayList<>())
                                .stream()
                                .map(image -> image.getPk().getImageUuid() + '.' + image.getExtension())
                                .collect(Collectors.toList()))
                        .createdDate(post.getCreatedDate())
                        .updatedDate(post.getUpdatedDate())
                        .isLike(likeRepository.existsById(new Like.Pk(member.getSeq(), post.getSeq())))
                        .build();
    }
    public List<PostNewsfeedDto> getNewsFeed(Long memberSeq, Pageable pageable){
        Page<Post> posts = postRepository.findByMemberSeq(memberSeq, pageable).orElseThrow(() -> new NotFoundPostException(CommonErrorCode.NOT_FOUND_POST));
        return posts.getContent().stream().map(post -> PostNewsfeedDto.builder()
                        .content(post.getContent())
                        .writer(post.getWriter().getName())
                        .placeName(post.getPlace().getPlaceName())
                        .imageList(imageRepository.findByPk_PostSeq(post.getSeq())
                                .orElse(new ArrayList<>())
                                .stream()
                                .map(image -> image.getPk().getImageUuid() + '.' + image.getExtension())
                                .collect(Collectors.toList()))
                        .createdDate(post.getCreatedDate())
                        .updatedDate(post.getUpdatedDate())
                        .isLike(likeRepository.existsById(new Like.Pk(memberSeq, post.getSeq())))
                        .build())
                .collect(Collectors.toList());
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
        Place place = placeRepository.findByPlaceName(postRequestDto.getPlaceName()).orElseThrow();
        post.update(postRequestDto.getContent(), place);
        return new CommonResponseDto("OK");
    }
    public Member getMember(){
        String userName = AuthenticationUtil.authenticationGetUsername();
        return memberRepository.findByName(userName).orElseThrow(() -> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));
    }
}
