package com.ssafy.enjoytrip.follow.controller;

import com.ssafy.enjoytrip.follow.service.FollowService;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;


}
