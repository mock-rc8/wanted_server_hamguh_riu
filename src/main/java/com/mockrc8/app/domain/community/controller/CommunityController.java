package com.mockrc8.app.domain.community.controller;


import com.mockrc8.app.domain.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping()
    public ResponseEntity<Object> getCommunityPosts(){
        communityService.getCommunityPosts()
    }
}
