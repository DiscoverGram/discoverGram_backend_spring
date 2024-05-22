package com.ssafy.enjoytrip.domain.follow.domain;

import com.ssafy.enjoytrip.domain.follow.dto.FollowDto;
import com.ssafy.enjoytrip.domain.member.domain.Member;
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
    @MapsId("followedMemberSeq")
    @JoinColumn(name = "followed_member_seq")
    private Member followedMember;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "follow_member_seq")
        private Long followMemberSeq;
        @Column(name = "followed_member_seq")
        private Long followedMemberSeq;
    }

    public FollowDto followToDto(boolean isFollow){

        return FollowDto.builder()
                .name(followMember.getName())
                .userProfileImage(followMember.getUserProfileImage())
                .isFollow(isFollow)
                .build();
    }

    public FollowDto followedToDto(boolean isFollow){

        return FollowDto.builder()
                .name(followedMember.getName())
                .userProfileImage(followedMember.getUserProfileImage())
                .isFollow(isFollow)
                .build();
    }
}