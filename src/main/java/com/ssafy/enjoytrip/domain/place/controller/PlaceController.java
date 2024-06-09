package com.ssafy.enjoytrip.domain.place.controller;

import com.ssafy.enjoytrip.domain.place.dto.PlaceResponseDto;
import com.ssafy.enjoytrip.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/place")
    public ResponseEntity<List<PlaceResponseDto>> searchPlaceList(@RequestParam Double latitude, @RequestParam Double longitude){
        return ResponseEntity.ok(placeService.searchPlaceList(latitude, longitude));
    }
}
