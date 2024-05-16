package com.ssafy.enjoytrip.post.domain;

import com.ssafy.enjoytrip.common.BaseTime;
import com.ssafy.enjoytrip.member.domain.Member;
import com.ssafy.enjoytrip.place.domain.Place;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    @ColumnDefault("0")
    private Long likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq", nullable = false)
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_seq", nullable = false)
    private Place place;

}
