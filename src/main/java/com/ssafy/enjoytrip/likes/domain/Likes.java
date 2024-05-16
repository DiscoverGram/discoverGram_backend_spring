package com.ssafy.enjoytrip.likes.domain;

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
public class Likes {

    @EmbeddedId
    private Pk pk;

    @MapsId("memberSeq")
    @JoinColumn(name = "member_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member Member;

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
