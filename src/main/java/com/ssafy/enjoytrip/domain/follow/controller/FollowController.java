package com.ssafy.enjoytrip.domain.follow.controller;

import com.ssafy.enjoytrip.domain.follow.dto.FollowDto;
import com.ssafy.enjoytrip.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follows/{memberSeq}/{followedMemberSeq}")
    public ResponseEntity<String> follow(@PathVariable("memberSeq") Long memberSeq, @PathVariable("followedMemberSeq") Long followedMemberSeq) {
        boolean result = followService.follow(memberSeq, followedMemberSeq);

        if(result){
            return ResponseEntity.ok("팔로우 완료");
        }
        return ResponseEntity.ok("실패");
    }

    @DeleteMapping("/followings/{memberSeq}/{followedMemberSeq}")
    public ResponseEntity<String> deleteFollowing( @PathVariable("memberSeq") Long memberSeq, @PathVariable("followedMemberSeq") Long followedMemberSeq){
         boolean result = followService.deleteFollow(memberSeq, followedMemberSeq);

         if(result){
             return ResponseEntity.ok("언팔로우 완료");
         }
         return ResponseEntity.ok("실패");
    }

    @DeleteMapping("/followers/{memberSeq}/{followedMemberSeq}")
    public ResponseEntity<String> deleteFollower( @PathVariable("memberSeq") Long memberSeq, @PathVariable("followedMemberSeq") Long followedMemberSeq ){
        boolean result = followService.deleteFollower(memberSeq, followedMemberSeq);
        
        if(result){
            return ResponseEntity.ok("팔로워 삭제 완료");
        }
        return ResponseEntity.ok("실패");
    }


    @GetMapping("/followings/{memberSeq}")
    public List<FollowDto> getFollowing(@PathVariable("memberSeq") Long memberSeq){
        return followService.getFollowing(memberSeq);
    }

    @GetMapping("/followers/{memberSeq}")
    public List<FollowDto> getFollower(@PathVariable("memberSeq") Long memberSeq){
        return followService.getFollower(memberSeq);
    }
}
