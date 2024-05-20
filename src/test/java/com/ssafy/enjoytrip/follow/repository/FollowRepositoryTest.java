package com.ssafy.enjoytrip.follow.repository;//package com.ssafy.enjoytrip.follow.repository;

import com.ssafy.enjoytrip.domain.follow.domain.Follow;
import com.ssafy.enjoytrip.domain.follow.dto.FollowDto;
import com.ssafy.enjoytrip.domain.follow.repository.FollowRepository;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FollowRepositoryTest {

    @Autowired
    TestEntityManager em;
    @Autowired
    FollowRepository followRepository;


    private Member followedMember;
    private Member followMember;
    private Follow follow;
    private FollowDto followDto;
    private FollowDto followedDto;

    private Long followMemberSeq = 2L;
    private Long followedMemberSeq = 1L;

    @BeforeEach
    void init() {
        // follow <- member
        followMember = Member.builder()
                .id("user2")
                .name("member")
                .password("password")
                .role("USER")
                .build();

        followedMember = Member.builder()
                .id("user1")
                .name("followed")
                .password("password")
                .role("USER")
                .build();

        follow = Follow.builder()
                .pk(new Follow.Pk(1L, 2L))
                .followMember(followMember)
                .followedMember(followedMember)
                .build();

        followDto = FollowDto.builder()
                .name("member")
                .userProfileImage("src")
                .build();

        followedDto = FollowDto.builder()
                .name("followed")
                .userProfileImage("src")
                .build();

        em.persist(followMember);
        em.persist(followedMember);
        em.persistAndFlush(follow);
    }

    @Test
    @DisplayName("팔로잉 체크")
    void existsByFollowMember_SeqAndFollowedMember_Seq() {
        // given

        // when
        boolean exists = followRepository.existsByFollowMember_SeqAndFollowedMember_Seq(1L, 2L);

        // then
        assertThat(exists).isTrue();
    }


    @Test
    @DisplayName("팔로잉 리스트 출력")
    void findFollowingList() {
        // given
        List<FollowDto> expect = Arrays.asList(followedDto);

        // when
        // followed가 following에 있음.
        List<Follow> followList = followRepository.findFollowedList(followedMemberSeq);
        List<FollowDto> result = new ArrayList<>();
        for (Follow follow1 : followList) {
            result.add(follow1.followedToDto());
        }

        // then
        assertThat(result.get(0).getName()).isEqualTo(expect.get(0).getName());
    }

    @Test
    @DisplayName("팔로워 리스트 출력")
    void findFollowerDtoList() {
        // given
        List<FollowDto> expect = Arrays.asList(followedDto);

        // when
        List<Follow> followList = followRepository.findFollowList(followMemberSeq);
        List<FollowDto> result = new ArrayList<>();
        for (Follow follow1 : followList) {
            result.add(follow1.followedToDto());
        }

        // then
        assertThat(result.get(0).getName()).isEqualTo(expect.get(0).getName());
    }


    @Test
    @DisplayName("팔로잉 숫자세기")
    void countByFollowMemberSeq() {
        // given
        int expect = 1;

        // when
        int actualCount = followRepository.countByFollowMemberSeq(followedMemberSeq);

        // then
        assertThat(actualCount).isEqualTo(expect);
    }

    @Test
    @DisplayName("팔로워 숫자세기")
    void testCountByFollowedMemberSeq() {
        // given
        int expect = 1;

        // when
        int actualCount = followRepository.countByFollowedMemberSeq(followMemberSeq);

        // then
        assertThat(actualCount).isEqualTo(expect);
    }
}