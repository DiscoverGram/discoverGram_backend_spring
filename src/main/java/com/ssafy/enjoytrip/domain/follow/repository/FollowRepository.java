package com.ssafy.enjoytrip.domain.follow.repository;

import com.ssafy.enjoytrip.domain.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Follow.Pk> {

}
