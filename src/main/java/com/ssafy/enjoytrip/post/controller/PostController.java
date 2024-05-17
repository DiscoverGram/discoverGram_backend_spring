package com.ssafy.enjoytrip.post.controller;

import com.ssafy.enjoytrip.post.domain.Post;
import com.ssafy.enjoytrip.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

}
