package com.ssafy.enjoytrip.domain.follow.service;

import com.ssafy.enjoytrip.domain.follow.domain.Follow;
import com.ssafy.enjoytrip.domain.follow.dto.FollowDto;
import com.ssafy.enjoytrip.domain.follow.repository.FollowRepository;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import com.ssafy.enjoytrip.global.error.CommonErrorCode;
import com.ssafy.enjoytrip.global.error.exception.AlreadyFollowException;
import com.ssafy.enjoytrip.global.error.exception.NotFoundMemberException;
import com.ssafy.enjoytrip.global.error.exception.SelfFollowException;
import com.ssafy.enjoytrip.global.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public boolean follow(Long memberSeq, Long followedMemberSeq) {
        if(Objects.equals(memberSeq, followedMemberSeq)){
            throw new SelfFollowException(CommonErrorCode.SELF_FOLLOW);
        }

        if(followRepository.existsByFollowMember_SeqAndFollowedMember_Seq(memberSeq, followedMemberSeq)){
            throw new AlreadyFollowException(CommonErrorCode.ALREADY_FOLLOW);
        }

        Follow follow = getFollowMemberFollowedMember(memberSeq, followedMemberSeq);

        followRepository.save(follow);

        return true;
    }

    public boolean deleteFollow(Long memberSeq, Long followedMemberSeq) {
        if(Objects.equals(memberSeq, followedMemberSeq)){
            throw new SelfFollowException(CommonErrorCode.SELF_FOLLOW);
        }

        Follow follow = getFollowMemberFollowedMember(memberSeq, followedMemberSeq);

        followRepository.delete(follow);

        return true;
    }


    public boolean deleteFollower(Long memberSeq, Long followedMemberSeq) {
        if(Objects.equals(memberSeq, followedMemberSeq)){
            throw new SelfFollowException(CommonErrorCode.SELF_FOLLOW);
        }

        Follow follow = getFollowMemberFollowedMember(memberSeq, followedMemberSeq);

        followRepository.delete(follow);

        return true;
    }

    public List<FollowDto> getFollowing(Long memberSeq, Pageable pageable) {
        Page<Follow> pages = followRepository.findAllByFollowMember_Seq(memberSeq, pageable).orElseThrow(() -> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));

        List<Follow> followList = pages.getContent().stream().collect(Collectors.toList());
        List<FollowDto> followDtoList = new ArrayList<>();

        for (Follow follow : followList) {
            followDtoList.add(follow.followedToDto(true));
        }

        return followDtoList;
    }

    public List<FollowDto> getFollower(Long memberSeq, Pageable pageable) {
        Page<Follow> pages = followRepository.findAllByFollowedMember_Seq(memberSeq, pageable).orElseThrow(() -> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));

        List<Follow> followList = pages.getContent().stream().collect(Collectors.toList());
        List<FollowDto> followedDtoList = new ArrayList<>();

        for (Follow follow : followList) {
            followedDtoList.add(follow.followToDto(true));
        }

        return followedDtoList;
    }

    private Follow getFollowMemberFollowedMember(Long memberSeq, Long followedMemberSeq) {
        Member member = memberRepository.findBySeq(memberSeq)
                .orElseThrow(() -> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));
        Member followedMember = memberRepository.findBySeq(followedMemberSeq)
                .orElseThrow(() -> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));

        Follow follow = Follow.builder()
                .pk(new Follow.Pk(memberSeq, followedMemberSeq))
                .followMember(member)
                .followedMember(followedMember)
                .build();

        return follow;
    }

    public boolean isFollow(Long followMemberSeq, Long memberSeq) {
        return followRepository.existsByFollowMember_SeqAndFollowedMember_Seq(followMemberSeq,memberSeq);
    }

    public Member getMember(){
        String userName = AuthenticationUtil.authenticationGetUsername();
        return memberRepository.findByName(userName).orElseThrow(() -> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));
    }

}
