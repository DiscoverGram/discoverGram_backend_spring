package com.ssafy.enjoytrip.domain.like.repository;

import com.ssafy.enjoytrip.domain.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Like.Pk> {
    Long countByPk(Like.Pk pk);
}
