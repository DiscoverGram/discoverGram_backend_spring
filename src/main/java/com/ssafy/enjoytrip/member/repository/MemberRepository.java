package com.ssafy.enjoytrip.member.repository;

import com.ssafy.enjoytrip.member.domain.Member;
import com.ssafy.enjoytrip.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.member.dto.MemberUpdateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySeq(Long memberSeq);
    Optional<Member> findById(String memberId);
    Boolean existsById(String id);
    void deleteBySeq(Long memberSeq);
}