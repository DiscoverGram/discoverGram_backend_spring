package com.ssafy.enjoytrip.place.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponseDto {
    private Long seq;
    private String placeName;
    private Double latitude;
    private Double longitude;
}


