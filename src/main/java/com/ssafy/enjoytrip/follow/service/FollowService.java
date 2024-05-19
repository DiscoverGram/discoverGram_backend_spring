package com.ssafy.enjoytrip.follow.service;

import com.ssafy.enjoytrip.error.CommonErrorCode;
import com.ssafy.enjoytrip.error.exception.AlreadyFollowException;
import com.ssafy.enjoytrip.error.exception.NotFoundUserException;
import com.ssafy.enjoytrip.error.exception.SelfFollowException;
import com.ssafy.enjoytrip.follow.domain.Follow;
import com.ssafy.enjoytrip.follow.dto.FollowDto;
import com.ssafy.enjoytrip.follow.repository.FollowRepository;
import com.ssafy.enjoytrip.member.domain.Member;
import com.ssafy.enjoytrip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public boolean follow(Long followMemberSeq, Long memberSeq) {
        if(memberSeq == followMemberSeq){
            throw new SelfFollowException(CommonErrorCode.SELF_FOLLOW);
        }

        if(followRepository.existsByFollowMemberSeqAndMemberSeq(followMemberSeq, memberSeq)){
            throw new AlreadyFollowException(CommonErrorCode.ALREADY_FOLLOW);
        }

        Follow follow = getMemberFollowedFollow(followMemberSeq, memberSeq);

        followRepository.save(follow);

        return true;
    }

    public boolean deleteFollow(Long followMemberSeq, Long memberSeq) {
        if(memberSeq == followMemberSeq){
            throw new SelfFollowException(CommonErrorCode.SELF_FOLLOW);
        }

        Follow follow = getMemberFollowedFollow(followMemberSeq, memberSeq);

        followRepository.delete(follow);

        return true;
    }


    public boolean deleteFollower(Long followedMemberSeq, Long memberSeq) {
        if(memberSeq == followedMemberSeq){
            throw new SelfFollowException(CommonErrorCode.SELF_FOLLOW);
        }

        Follow follow = getMemberFollowedFollow(followedMemberSeq, memberSeq);

        followRepository.delete(follow);

        return true;
    }

    public List<FollowDto> getFollowing(Long memberSeq) {
        List<Long> followingList = followRepository.findFollowingList(memberSeq);

        List<FollowDto> followingDtoList = new ArrayList<>();
        followingDtoList = followRepository.findFollowingDtoList(followingList);

        return followingDtoList;
    }

    public List<FollowDto> getFollower(Long memberSeq) {
        List<Long> followerList = followRepository.findFollowerList(memberSeq);

        List<FollowDto> followerDtoList = new ArrayList<>();
        followerDtoList = followRepository.findFollowerDtoList(followerList);

        return followerDtoList;
    }



    private Follow getMemberFollowedFollow(Long followMemberSeq, Long memberSeq) {
        Member member = memberRepository.findBySeq(memberSeq)
                .orElseThrow(() -> new NotFoundUserException(CommonErrorCode.NOT_FOUND_USER));
        Member followMember = memberRepository.findBySeq(followMemberSeq)
                .orElseThrow(() -> new NotFoundUserException(CommonErrorCode.NOT_FOUND_USER));

        Follow follow = new Follow().builder()
                .followMember(followMember)
                .followedMember(member)
                .build();
        return follow;
    }

}
