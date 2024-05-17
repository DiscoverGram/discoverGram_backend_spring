package com.ssafy.enjoytrip.member.controller;

import com.ssafy.enjoytrip.error.CommonErrorCode;
import com.ssafy.enjoytrip.error.exception.BindingException;
import com.ssafy.enjoytrip.member.dto.MemberRequestDto;
import com.ssafy.enjoytrip.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.member.dto.MemberUpdateDto;
import com.ssafy.enjoytrip.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) throw new BindingException(CommonErrorCode.BINDING_ERROR,bindingResult.getFieldError().getDefaultMessage());
        memberService.signUp(memberRequestDto);
        return ResponseEntity.ok("회원가입완료");
    }

    @GetMapping("/members/{memberSeq}")
    public MemberResponseDto memberDetail(@RequestParam Long memberSeq){
        return memberService.findById(memberSeq);
    }

    @PutMapping("/members/{memberSeq}")
    public ResponseEntity<String> updateMember(@Valid @RequestBody MemberUpdateDto memberUpdateDto, BindingResult bindingResult, @PathVariable("memberId") Long memberSeq) {
        if(bindingResult.hasErrors()) throw new BindingException(CommonErrorCode.BINDING_ERROR,bindingResult.getFieldError().getDefaultMessage());
        memberService.updateMember(memberUpdateDto, memberSeq);
        return ResponseEntity.ok("수정완료");
    }

    @DeleteMapping("/members/{memberSeq}")
    public ResponseEntity<String> deleteMember(@RequestParam Long memberSeq){
        memberService.deleteMember(memberSeq);
    }
}
