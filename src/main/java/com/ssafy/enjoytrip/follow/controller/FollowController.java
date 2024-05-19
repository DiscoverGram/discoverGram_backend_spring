package com.ssafy.enjoytrip.follow.controller;

import com.ssafy.enjoytrip.follow.dto.FollowDto;
import com.ssafy.enjoytrip.follow.service.FollowService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follows/{followMemberSeq}/{memberSeq}")
    public ResponseEntity<String> follow(@PathVariable("followMemberSeq") Long followMemberSeq, @PathVariable("memberSeq") Long memberSeq) {
        boolean result = followService.follow(followMemberSeq, memberSeq);

        if(result){
            return ResponseEntity.ok("팔로우 완료");
        }
        return ResponseEntity.ok("실패");
    }

    @DeleteMapping("/followings/{followMemberSeq}/{memberSeq}")
    public ResponseEntity<String> deleteFollowing(@PathVariable("followMemberSeq") Long followMemberSeq, @PathVariable("memberSeq") Long memberSeq){
         boolean result = followService.deleteFollow(followMemberSeq, memberSeq);

         if(result){
             return ResponseEntity.ok("언팔로우 완료");
         }
         return ResponseEntity.ok("실패");
    }

    @DeleteMapping("/followers/{followedMemberSeq}/{memberSeq}")
    public ResponseEntity<String> deleteFollower(@PathVariable("followedMemberSeq") Long followedMemberSeq, @PathVariable("memberSeq") Long memberSeq){
        boolean result = followService.deleteFollower(followedMemberSeq, memberSeq);
        
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
