package com.ssafy.enjoytrip.member.domain;

import com.ssafy.enjoytrip.member.dto.MemberRequestDto;
import com.ssafy.enjoytrip.member.dto.MemberResponseDto;
import com.ssafy.enjoytrip.member.dto.MemberUpdateDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Long seq;
    @Column(name = "member_id", nullable = false, unique = true)
    private String id;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String role;
    @Column
    private String userProfileImage;

    public MemberResponseDto update(MemberUpdateDto memberUpdateDto){
        name = memberUpdateDto.getName();
        password = memberUpdateDto.getPassword();
        userProfileImage = memberUpdateDto.getUserProfileImage();

        return toResponseDto();
    }

    public MemberResponseDto toResponseDto(){
        return MemberResponseDto.builder()
                .seq(seq)
                .id(id)
                .password(password)
                .name(name)
                .userProfileImage(userProfileImage)
                .build();
    }
}
