package com.ssafy.enjoytrip.follow.repository;

import com.ssafy.enjoytrip.follow.domain.Follow;
import com.ssafy.enjoytrip.follow.dto.FollowDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowMemberSeqAndMemberSeq(Long followMemberSeq, Long memberSeq);

    // 팔로잉(내가 따르는) 애들 출력하기
    @Query("SELECT new com.ssafy.enjoytrip.follow.dto.FollowDto("
            + "new com.ssafy.enjoytrip.member.dto.MemberResponseDto(m)) "
            + "FROM Member m "
            + "WHERE m.memberSeq IN :followedMemberSeqList")
    List<FollowDto> findFollowingDtoList(@Param("followedMemberSeqList") List<Long> followedMemberSeqList);

    // 팔로잉(내가 따르는) 목록 번호
    @Query("SELECT f.followMember.seq FROM Follow f WHERE f.followedMember.seq = :memberSeq")
    List<Long> findFollowingList(@Param("memberSeq") Long memberSeq);

    // 팔로워(나를 따르는) 애들 출력하기
    @Query("SELECT new com.ssafy.enjoytrip.follow.dto.FollowDto("
            + "new com.ssafy.enjoytrip.member.dto.MemberResponseDto(m)) "
            + "FROM Member m "
            + "WHERE m.memberSeq IN :followMemberSeqList")
    List<FollowDto> findFollowerDtoList(@Param("followMemberSeqList") List<Long> followMemberSeqList);

    // 팔로워(나를 따르는) 목록 번호
    @Query("SELECT f.followedMember.seq FROM Follow f WHERE f.followMember.seq = :memberSeq")
    List<Long> findFollowerList(@Param("memberSeq") Long memberSeq);


    int countByFollowMemberSeq(Long followMemberSeq);

    int countByFollowedMemberSeq(Long followedMemberSeq);
}
