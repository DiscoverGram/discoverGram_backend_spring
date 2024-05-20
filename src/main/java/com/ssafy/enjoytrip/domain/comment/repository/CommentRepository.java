package com.ssafy.enjoytrip.domain.comment.repository;

import com.ssafy.enjoytrip.domain.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Page<Comment>> findAllByPost_Seq(Long postSeq, Pageable pageable);

}
