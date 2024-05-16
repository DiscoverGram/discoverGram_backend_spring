package com.ssafy.enjoytrip.member.service;

import com.ssafy.enjoytrip.error.CommonErrorCode;
import com.ssafy.enjoytrip.error.exception.UserExistException;
import com.ssafy.enjoytrip.member.domain.Member;
import com.ssafy.enjoytrip.member.dto.MemberRequestDto;
import com.ssafy.enjoytrip.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String signUp(MemberRequestDto memberRequestDto){
        if(memberRepository.existsById(memberRequestDto.getId()))
            throw new UserExistException(CommonErrorCode.USER_EXIST);
        Member member = Member.builder()
                .id(memberRequestDto.getId())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .name(memberRequestDto.getName())
                .build();

        memberRepository.save(member);

        return member.getId();
    }

    public MemberResponseDto findMemberById(String memberId) {
        return memberRepository.findMemberById(memberId);
    }
}
