package com.ssafy.enjoytrip.follow.repository;

import com.ssafy.enjoytrip.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
}
