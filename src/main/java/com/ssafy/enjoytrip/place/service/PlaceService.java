package com.ssafy.enjoytrip.place.service;

import com.ssafy.enjoytrip.place.domain.Place;
import com.ssafy.enjoytrip.place.dto.PlaceResponseDto;
import com.ssafy.enjoytrip.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public List<PlaceResponseDto> searchPlaceList(Double latitude, Double longitude){
        Optional<List<Place>> places = placeRepository.findByCoordinate(latitude, longitude);
        return places.map(placeList -> placeList.stream().map(m -> PlaceResponseDto.builder()
                        .placeName(m.getPlaceName())
                        .seq(m.getSeq())
                        .latitude(m.getLatitude())
                        .longitude(m.getLongitude())
                        .build())
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }
}
