package com.ssafy.enjoytrip.hashtag.service;

import com.ssafy.enjoytrip.hashtag.repository.HashTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class HashTagService {
    private final HashTagRepository hashTagRepository;
    무말알;

}
