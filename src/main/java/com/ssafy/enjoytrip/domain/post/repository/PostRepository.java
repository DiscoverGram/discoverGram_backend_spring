package com.ssafy.enjoytrip.domain.post.repository;


import com.ssafy.enjoytrip.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p JOIN Follow f ON p.writer.seq = f.followedMember.seq WHERE f.followMember.seq = ?1")
    Optional<Page<Post>> findByNewsfeed(Long memberSeq, Pageable pageable);
}
