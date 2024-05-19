package com.ssafy.enjoytrip.place.repository;

import com.ssafy.enjoytrip.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    // 지도 크기에 따라 값 변경해야함
    @Query("SELECT p FROM places p WHERE p.latitude BETWEEN :latitude - 0.0001 AND :latitude + 0.0001 " +
            "AND p.longitude BETWEEN :longitude - 0.0001 AND :longidute + 0.0001")
    Optional<List<Place>> findByCoordinate(Double latitude, Double longitude);
}
