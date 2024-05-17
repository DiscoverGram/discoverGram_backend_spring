package com.ssafy.enjoytrip.hashtag.repository;

import com.ssafy.enjoytrip.hashtag.domain.HashTag;
import com.ssafy.enjoytrip.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    @Query("SELECT h.post FROM hashtag h WHERE h.tagSeq = :tagSeq")
    Optional<Page<Post>> findPostsByTagSeq(@Param("tagSeq") Long tagSeq, Pageable pageable);
}
