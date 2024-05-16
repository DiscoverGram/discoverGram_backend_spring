package com.ssafy.enjoytrip.hashtag.domain;

import com.ssafy.enjoytrip.post.domain.Post;
import com.ssafy.enjoytrip.tag.domain.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HashTag {

    @EmbeddedId
    @Column(name = "post_seq")
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
