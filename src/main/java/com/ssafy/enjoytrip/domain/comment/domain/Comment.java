package com.ssafy.enjoytrip.domain.comment.domain;

import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.global.common.BaseTime;
import com.ssafy.enjoytrip.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_seq")
    private Long commentSeq;

    @JoinColumn(name = "post_seq", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member memberSeq;

    @Column(nullable = false)
    private String content;
}
