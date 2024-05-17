package com.ssafy.enjoytrip.follow.domain;

import com.ssafy.enjoytrip.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "follows")
public class Follow {

    @EmbeddedId
    private Pk pk;

    @MapsId("followMemberSeq")
    @JoinColumn(name = "follow_member_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member followMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followerMemberSeq")
    @JoinColumn(name = "follower_member_seq")
    private Member followerMember;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "follow_member_seq")
        private Long followMemberSeq;
        @Column(name = "follower_member_seq")
        private Long followerMemberSeq;
    }
}
