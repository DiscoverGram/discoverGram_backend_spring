package com.ssafy.enjoytrip.domain.tag.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_seq")
    private Long seq;
    @Column(length = 50, nullable = false)
    private String tag;
}
