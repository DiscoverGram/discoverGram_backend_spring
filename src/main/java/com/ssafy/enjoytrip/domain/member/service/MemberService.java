package com.ssafy.enjoytrip.domain.member.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.enjoytrip.domain.follow.repository.FollowRepository;
import com.ssafy.enjoytrip.domain.image.domain.Image;
import com.ssafy.enjoytrip.domain.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.domain.member.dto.MemberUpdateDto;
import com.ssafy.enjoytrip.global.common.CommonResponseDto;
import com.ssafy.enjoytrip.global.error.CommonErrorCode;
import com.ssafy.enjoytrip.global.error.exception.NotFoundMemberException;
import com.ssafy.enjoytrip.global.error.exception.UserExistException;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.member.dto.MemberRequestDto;
import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final MemberRepository memberRepository;
    private final AmazonS3Client amazonS3Client;
    private final PasswordEncoder passwordEncoder;
    private final FollowRepository followRepository;


    public CommonResponseDto signUp(MemberRequestDto memberRequestDto, MultipartFile file) {
        if(memberRepository.existsById(memberRequestDto.getId()))
            throw new UserExistException(CommonErrorCode.USER_EXIST);
        Member member = Member.builder()
                .id(memberRequestDto.getId())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .name(memberRequestDto.getName())
                .role("ROLE_USER")
                .build();
        String profileImage = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

        member.addProfile(profileImage);

        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        try{
            amazonS3Client.putObject(bucket, profileImage, file.getInputStream(),metadata);
            log.info("들어가유");
        }catch (Exception e){
            e.printStackTrace();
        }
        memberRepository.save(member);

        return new CommonResponseDto("OK");
    }

    public MemberResponseDto detailMember(Long memberSeq){
        Member member = memberRepository.findBySeq(memberSeq).orElseThrow(()-> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));
        Long followingNum = followRepository.countFollowByFollowMember_Seq(memberSeq);
        Long followerNum = followRepository.countFollowByFollowedMember_Seq(memberSeq);

        return member.toResponseDto(followingNum, followerNum);
    }

    public MemberResponseDto updateMember(MemberUpdateDto memberUpdateDto, Long memberSeq) {
        Member member = memberRepository.findBySeq(memberSeq).orElseThrow();
        return member.update(memberUpdateDto);
    }

    public CommonResponseDto deleteMember(Long memberSeq) {
        memberRepository.findBySeq(memberSeq).orElseThrow(()-> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));
        return new CommonResponseDto("OK");
    }
}
