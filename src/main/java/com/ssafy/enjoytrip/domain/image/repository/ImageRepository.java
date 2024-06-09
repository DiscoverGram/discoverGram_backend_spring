package com.ssafy.enjoytrip.domain.image.repository;

import com.ssafy.enjoytrip.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Image.Pk> {
    Optional<List<Image>> findByPk_PostSeq(Long postSeq);
}
