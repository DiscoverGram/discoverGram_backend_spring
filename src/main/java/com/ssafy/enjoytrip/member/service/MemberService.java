package com.ssafy.enjoytrip.member.service;

import com.ssafy.enjoytrip.error.CommonErrorCode;
import com.ssafy.enjoytrip.error.exception.NotFoundUserException;
import com.ssafy.enjoytrip.error.exception.UserExistException;
import com.ssafy.enjoytrip.member.domain.Member;
import com.ssafy.enjoytrip.member.dto.MemberRequestDto;
import com.ssafy.enjoytrip.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.member.dto.MemberUpdateDto;
import com.ssafy.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public MemberResponseDto findById(Long memberSeq){
        Member member = memberRepository.findBySeq(memberSeq).orElseThrow(()-> new NotFoundUserException(CommonErrorCode.NOT_FOUND_USER));
        return member.toResponseDto();
    }


    public void updateMember(MemberUpdateDto memberUpdateDto, Long memberSeq) {
        Member member = memberRepository.findBySeq(memberSeq).orElseThrow();
        member.update(memberUpdateDto);
    }

    public void deleteMember(Long memberSeq) {
        memberRepository.findBySeq(memberSeq).orElseThrow(()-> new NotFoundUserException(CommonErrorCode.NOT_FOUND_USER));
        memberRepository.deleteById(memberSeq);
    }
}
