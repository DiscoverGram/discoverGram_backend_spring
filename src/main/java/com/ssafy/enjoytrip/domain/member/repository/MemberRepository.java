package com.ssafy.enjoytrip.domain.member.repository;

import com.ssafy.enjoytrip.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySeq(Long memberSeq);

    Optional<Member> findById(String id);
    Optional<Member> findByName(String name);
    Boolean existsById(String id);
}