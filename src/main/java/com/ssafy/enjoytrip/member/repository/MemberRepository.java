package com.ssafy.enjoytrip.member.repository;

import com.ssafy.enjoytrip.member.domain.Member;
import com.ssafy.enjoytrip.member.dto.MemberResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String id);
    Boolean existsById(String id);

    MemberResponseDto findMemberById(String memberId);
}