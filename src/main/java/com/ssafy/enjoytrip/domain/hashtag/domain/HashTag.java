package com.ssafy.enjoytrip.domain.hashtag.domain;

import com.ssafy.enjoytrip.domain.tag.domain.Tag;
import com.ssafy.enjoytrip.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hashTags")
public class HashTag {

    @EmbeddedId
    private Pk pk;

    @MapsId("postSeq")
    @JoinColumn(name = "post_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @MapsId("tagSeq")
    @JoinColumn(name = "tag_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "post_seq")
        private Long postSeq;
        @Column(name = "tag_seq")
        private Long tagSeq;
    }
}
