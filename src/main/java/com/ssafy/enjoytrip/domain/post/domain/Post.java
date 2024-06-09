package com.ssafy.enjoytrip.domain.post.domain;

import com.ssafy.enjoytrip.domain.image.domain.Image;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.place.domain.Place;
import com.ssafy.enjoytrip.domain.post.dto.PostResponseDto;
import com.ssafy.enjoytrip.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_seq")
    private Long seq;
    @Column(length = 500, nullable = false)
    private String content;
    @Column(nullable = false)
    private String thumbnailImage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq", nullable = false)
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_seq", nullable = false)
    private Place place;

    public void addImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }
    public void update(String content, Place place){
        this.content = content;
        this.place = place;
    }
    public static PostResponseDto PostToDto(Post post){
        return PostResponseDto.builder()
                .postSeq(post.getSeq())
                .thumbnailImage(post.getThumbnailImage())
                .build();
    }
}
