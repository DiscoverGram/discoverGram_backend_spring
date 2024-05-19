package com.ssafy.enjoytrip.domain.like.domain;

import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="likes")
public class Like {

    @EmbeddedId
    private Pk pk;

    @MapsId("memberSeq")
    @JoinColumn(name = "member_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postSeq")
    @JoinColumn(name = "post_seq")
    private Post post;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "member_seq")
        private Long memberSeq;
        @Column(name = "post_seq")
        private Long postSeq;
    }
}
