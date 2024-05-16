package com.ssafy.enjoytrip.member.controller;

import com.ssafy.enjoytrip.error.CommonErrorCode;
import com.ssafy.enjoytrip.error.exception.BindingException;
import com.ssafy.enjoytrip.member.dto.MemberRequestDto;
import com.ssafy.enjoytrip.member.dto.MemberResponseDto;
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

    @PostMapping(value = "sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) throw new BindingException(CommonErrorCode.BINDING_ERROR,bindingResult.getFieldError().getDefaultMessage());
        memberService.signUp(memberRequestDto);
        return ResponseEntity.ok("회원가입완료");
    }

    @GetMapping(value = "member")
    public MemberResponseDto memberDetail(@RequestParam String memberId){
        return memberService.findMemberById(memberId);
    }
}
