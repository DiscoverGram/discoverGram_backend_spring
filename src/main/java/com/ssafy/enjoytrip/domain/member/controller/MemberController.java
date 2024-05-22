package com.ssafy.enjoytrip.domain.member.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.ssafy.enjoytrip.domain.member.dto.MemberRequestDto;
import com.ssafy.enjoytrip.domain.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.domain.member.dto.MemberUpdateDto;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import com.ssafy.enjoytrip.global.error.CommonErrorCode;
import com.ssafy.enjoytrip.global.error.exception.BindingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @PostMapping(value = "/signup")
    public ResponseEntity<CommonResponseDto> signUp(@RequestPart("memberRequest") MemberRequestDto memberRequestDto, @RequestPart("file") MultipartFile file) {
//        if(bindingResult.hasErrors()) throw new BindingException(CommonErrorCode.BINDING_ERROR,bindingResult.getFieldError().getDefaultMessage());

        return ResponseEntity.ok(memberService.signUp(memberRequestDto, file));
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
