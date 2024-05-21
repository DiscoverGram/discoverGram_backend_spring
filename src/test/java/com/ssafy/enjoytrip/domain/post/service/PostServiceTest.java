package com.ssafy.enjoytrip.domain.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.ssafy.enjoytrip.domain.image.repository.ImageRepository;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import com.ssafy.enjoytrip.domain.place.domain.Place;
import com.ssafy.enjoytrip.domain.place.repository.PlaceRepository;
import com.ssafy.enjoytrip.domain.post.domain.Post;
import com.ssafy.enjoytrip.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
@Transactional
@WithMockUser
class PostServiceTest {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Mock
    AmazonS3Client amazonS3Client;
    @Mock
    PostRepository postRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    PlaceRepository placeRepository;
    @Mock
    ImageRepository imageRepository;
    @InjectMocks
    PostService postService;
    Post post;
    Member writer;
    Place place;

    @BeforeEach
    void init(){
        post = Post.builder()
                .content("내용티비")
                .writer(writer)
                .place(place)
                .build();

    }

    @Test
    @DisplayName("게시글 생성")
    void create() {

    }

    @Test
    void getNewsFeed() {
    }

    @Test
    void getFeed() {
    }

    @Test
    void update() {
    }

    @Test
    void getMember() {
    }
}