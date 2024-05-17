package com.ssafy.enjoytrip.tag.repository;

import com.ssafy.enjoytrip.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long>  {
    Boolean existsTagByTag(String tag);
}


