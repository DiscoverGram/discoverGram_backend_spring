package com.ssafy.enjoytrip.domain.place.domain;

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
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_seq")
    private Long seq;

    @Column(name = "place_name", nullable = false)
    private String placeName;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;
}
