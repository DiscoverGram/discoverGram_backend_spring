//package com.ssafy.enjoytrip.follow.repository;//package com.ssafy.enjoytrip.follow.repository;
//
//import com.ssafy.enjoytrip.domain.follow.domain.Follow;
//import com.ssafy.enjoytrip.domain.follow.dto.FollowDto;
//import com.ssafy.enjoytrip.domain.follow.repository.FollowRepository;
//import com.ssafy.enjoytrip.domain.member.domain.Member;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class FollowRepositoryTest {
//
//    @Autowired
//    TestEntityManager em;
//    @Autowired
//    FollowRepository followRepository;
//
//
//    private Member followedMember;
//    private Member followMember;
//    private Follow follow;
//    private FollowDto followDto;
//    private FollowDto followedDto;
////
////    private Long followMemberSeq = 2L;
////    private Long followedMemberSeq = 1L;
//
//    @BeforeEach
//    void init() {
//        // follow <- member
//        followMember = Member.builder()
//                .id("user2")
//                .name("member")
//                .password("password")
//                .role("USER")
//                .build();
//
//        followedMember = Member.builder()
//                .id("user1")
//                .name("followed")
//                .password("password")
//                .role("USER")
//                .build();
//
//
//
//        followDto = FollowDto.builder()
//                .name("member")
//                .userProfileImage("src")
//                .build();
//
//        followedDto = FollowDto.builder()
//                .name("followed")
//                .userProfileImage("src")
//                .build();
//
//        em.persist(followMember);
//        em.persist(followedMember);
//
//        follow = Follow.builder()
//                .pk(new Follow.Pk(followMember.getSeq(), followedMember.getSeq()))
//                .followMember(followMember)
//                .followedMember(followedMember)
//                .build();
//        em.persistAndFlush(follow);
//    }
//
//    @AfterEach
//    public void end(){
//        System.out.println("follow = " + follow.getFollowedMember().getSeq());
//        System.out.println("follow = " + follow.getFollowMember().getSeq());
//    }
//
//
//    @Test
//    @DisplayName("팔로잉 체크")
//    @Transactional
//    void existsByFollowMember_SeqAndFollowedMember_Seq() {
//        // given
//
//        // when
//        boolean exists = followRepository.existsByFollowMember_SeqAndFollowedMember_Seq(followMember.getSeq(), followedMember.getSeq());
//
//        // then
//        assertThat(exists).isTrue();
//    }
//
//
//    @Test
//    @DisplayName("팔로잉 리스트 출력")
//    @Transactional
//    void findFollowingList() {
//        // given
//        List<FollowDto> expect = Arrays.asList(followedDto);
//
//        // when
//        // followed가 following에 있음.
//        List<Follow> followList = followRepository.findFollowedList(followMember.getSeq());
//        List<FollowDto> result = new ArrayList<>();
//        for (Follow follow1 : followList) {
//            result.add(follow1.followedToDto());
//        }
//
//        // then
//        assertThat(result.get(0).getName()).isEqualTo(expect.get(0).getName());
//    }
//
//    @Test
//    @DisplayName("팔로워 리스트 출력")
//    @Transactional
//    void findFollowerDtoList() {
//        // given
//        List<FollowDto> expect = Arrays.asList(followedDto);
//
//        // when
//        List<Follow> followList = followRepository.findFollowList(followedMember.getSeq());
//        List<FollowDto> result = new ArrayList<>();
//        for (Follow follow1 : followList) {
//            result.add(follow1.followedToDto());
//        }
//
//        // then
//        assertThat(result.get(0).getName()).isEqualTo(expect.get(0).getName());
//    }
//
//
//    @Test
//    @DisplayName("팔로잉 숫자세기")
//    @Transactional
//    void countByFollowMemberSeq() {
//        // given
//        Long expect = 1L;
//
//        // when
//        Long actualCount = followRepository.countFollowByFollowMember_Seq(followMember.getSeq());
//
//        // then
//        assertThat(actualCount).isEqualTo(expect);
//    }
//
//    @Test
//    @DisplayName("팔로워 숫자세기")
//    @Transactional
//    void testCountByFollowedMemberSeq() {
//        // given
//        Long expect = 1L;
//
//        // when
//        Long actualCount = followRepository.countFollowByFollowedMember_Seq(followedMember.getSeq());
//
//        // then
//        assertThat(actualCount).isEqualTo(expect);
//    }
//}