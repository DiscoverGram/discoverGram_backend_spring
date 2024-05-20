package com.ssafy.enjoytrip.domain.member.controller;

import com.ssafy.enjoytrip.domain.member.dto.MemberRequestDto;
import com.ssafy.enjoytrip.domain.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.domain.member.dto.MemberUpdateDto;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
import com.ssafy.enjoytrip.global.error.CommonErrorCode;
import com.ssafy.enjoytrip.global.error.exception.BindingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) throw new BindingException(CommonErrorCode.BINDING_ERROR,bindingResult.getFieldError().getDefaultMessage());
        memberService.signUp(memberRequestDto);
        return ResponseEntity.ok("회원가입완료");
    }


    @GetMapping("/members/{memberSeq}")
    public MemberResponseDto detailMember(@PathVariable("memberSeq") Long memberSeq){
        return memberService.detailMember(memberSeq);
    }

    @PutMapping("/members/{memberSeq}")
    public MemberResponseDto updateMember(@Valid @RequestBody MemberUpdateDto memberUpdateDto, BindingResult bindingResult, @PathVariable("memberSeq") Long memberSeq) {
//        if(bindingResult.hasErrors()) throw new BindingException(CommonErrorCode.BINDING_ERROR,bindingResult.getFieldError().getDefaultMessage());
        return memberService.updateMember(memberUpdateDto, memberSeq);
    }

    @DeleteMapping("/members/{memberSeq}")
    public ResponseEntity<String> deleteMember(@PathVariable("memberSeq") Long memberSeq){
        memberService.deleteMember(memberSeq);
        return ResponseEntity.ok("삭제완료");
    }

}
