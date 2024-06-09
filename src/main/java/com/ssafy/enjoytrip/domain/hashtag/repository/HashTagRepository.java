package com.ssafy.enjoytrip.domain.hashtag.repository;

import com.ssafy.enjoytrip.domain.hashtag.domain.HashTag;
import com.ssafy.enjoytrip.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    @Query("SELECT h.post FROM HashTag h WHERE h.pk.tagSeq = ?1")
    Optional<Page<Post>> findPostsByTagSeq(Long tagSeq, Pageable pageable);
}
