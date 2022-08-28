package com.mockrc8.app.domain.community.controller;


import com.mockrc8.app.domain.community.dto.RegisterPostCommentDto;
import com.mockrc8.app.domain.community.dto.RegisterPostReqDto;
import com.mockrc8.app.domain.community.service.CommunityService;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.UserNotFoundException;
import com.mockrc8.app.global.oAuth.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping()
    public ResponseEntity<Object> getCommunityPosts(@RequestParam(required = false) Long tagId){

        return communityService.getCommunityPosts();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Object> getPostById(@PathVariable("postId") Integer postId){
        return communityService.getPostById(postId.longValue());

    }

//    @GetMapping()
//    public ResponseEntity<Object> getPostsByTagId(@RequestParam Integer tagId){
//        return communityService.getPostsByTagId(tagId.longValue());
//    }

//    @GetMapping("/{postId}/comments")
//    public ResponseEntity<Object> getPostComments(){
//        return communityService.getPostComments();
//    }
//    @GetMapping("/{postId}/likes")
//    public ResponseEntity<Object> getPostLikes(){
//        return communityService.getPostLikes();
//    }

    @PostMapping("/posting")
    public ResponseEntity<Long> registerPost(@CurrentUser String userEmail, @RequestBody RegisterPostReqDto registerPostReqDto){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return communityService.registerPost(userEmail,registerPostReqDto);
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<Long> registerPostComment(@CurrentUser String userEmail,
                                                    @PathVariable("postId") Integer postId,
                                                    @RequestBody RegisterPostCommentDto registerPostCommentDto){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        registerPostCommentDto.setPost_id(postId.longValue());
        return communityService.registerPostComment(userEmail,registerPostCommentDto);
    }
}
