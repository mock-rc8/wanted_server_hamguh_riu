package com.mockrc8.app.domain.community.service;

import com.mockrc8.app.domain.community.mapper.CommunityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityMapper communityMapper;
    public ResponseEntity<Object> getCommunityPosts() {
        communityMapper.getCommunityPosts()
    }
}
