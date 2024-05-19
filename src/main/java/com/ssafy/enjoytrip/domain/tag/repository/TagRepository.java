package com.ssafy.enjoytrip.domain.tag.repository;

import com.ssafy.enjoytrip.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>  {
    Boolean existsTagByTag(String tag);
}


