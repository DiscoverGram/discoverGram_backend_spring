package com.ssafy.enjoytrip.post.repository;


import com.ssafy.enjoytrip.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
