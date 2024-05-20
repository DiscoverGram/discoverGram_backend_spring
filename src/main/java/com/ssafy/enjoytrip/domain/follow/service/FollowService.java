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

    public boolean follow(Long memberSeq, Long followedMemberSeq) {
        if(memberSeq == followedMemberSeq){
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
        if(memberSeq == followedMemberSeq){
            throw new SelfFollowException(CommonErrorCode.SELF_FOLLOW);
        }

        Follow follow = getFollowMemberFollowedMember(memberSeq, followedMemberSeq);

        followRepository.delete(follow);

        return true;
    }


    public boolean deleteFollower(Long memberSeq, Long followedMemberSeq) {
        if(memberSeq == followedMemberSeq){
            throw new SelfFollowException(CommonErrorCode.SELF_FOLLOW);
        }

        Follow follow = getFollowMemberFollowedMember(memberSeq, followedMemberSeq);

        followRepository.delete(follow);

        return true;
    }

    public List<FollowDto> getFollowing(Long memberSeq) {
        List<Follow> followingList = followRepository.findFollowedList(memberSeq);

        List<FollowDto> followingDtoList = new ArrayList<>();

        for (Follow follow : followingList) {
            followingDtoList.add(follow.followToDto());
        }

        return followingDtoList;
    }

    public List<FollowDto> getFollower(Long memberSeq) {
        List<Follow> followerList = followRepository.findFollowList(memberSeq);

        List<FollowDto> followerDtoList = new ArrayList<>();

        for (Follow follow : followerList) {
            followerDtoList.add(follow.followedToDto());
        }

        return followerDtoList;
    }

    private Follow getFollowMemberFollowedMember(Long memberSeq, Long followedMemberSeq) {
        Member member = memberRepository.findBySeq(memberSeq)
                .orElseThrow(() -> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));
        Member followedMember = memberRepository.findBySeq(followedMemberSeq)
                .orElseThrow(() -> new NotFoundMemberException(CommonErrorCode.NOT_FOUND_MEMBER));

        Follow follow = new Follow().builder()
                .followMember(member)
                .followedMember(followedMember)
                .build();
        return follow;
    }

}
