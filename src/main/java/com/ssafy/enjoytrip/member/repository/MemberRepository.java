package com.ssafy.enjoytrip.member.repository;

import com.ssafy.enjoytrip.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String id);
    Boolean existsById(String id);
}