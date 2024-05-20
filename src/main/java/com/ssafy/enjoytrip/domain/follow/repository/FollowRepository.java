package com.ssafy.enjoytrip.domain.follow.repository;

import com.ssafy.enjoytrip.domain.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Follow.Pk> {
    boolean existsByFollowMember_SeqAndFollowedMember_Seq(Long memberSeq, Long followedMemberSeq);
    // 팔로잉(내가 따르는) 목록 번호
    @Query("SELECT f FROM Follow f WHERE f.followMember.seq = :memberSeq")
    List<Follow> findFollowedList(@Param("memberSeq") Long memberSeq);


    // 팔로워(나를 따르는) 목록 번호
    @Query("SELECT f FROM Follow f WHERE f.followedMember.seq = :memberSeq")
    List<Follow> findFollowList(@Param("memberSeq") Long memberSeq);


    int countFollowByFollowMember_Seq(Long followMemberSeq);
    int countFollowByFollowedMember_Seq(Long followedMemberSeq);
}
