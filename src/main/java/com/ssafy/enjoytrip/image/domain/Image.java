package com.ssafy.enjoytrip.image.domain;

import com.ssafy.enjoytrip.hashtag.domain.HashTag;
import com.ssafy.enjoytrip.member.domain.Member;
import com.ssafy.enjoytrip.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Image {
    @EmbeddedId
    private Pk pk;

    @MapsId("postSeq")
    @JoinColumn(name = "post_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
    @MapsId(value = "imageUuid")
    private String imageUuid;
    @Column(nullable = false)
    private String extension;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "post_seq")
        private Long postSeq;
        @Column(name = "image_uuid")
        private Long imageUuid;
    }
}
