package com.mockrc8.app.domain.community.controller;


import com.mockrc8.app.domain.community.dto.RegisterPostCommentDto;
import com.mockrc8.app.domain.community.dto.RegisterPostLikeDto;
import com.mockrc8.app.domain.community.dto.RegisterPostReqDto;
import com.mockrc8.app.domain.community.service.CommunityService;
import com.mockrc8.app.domain.community.vo.Comment;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.UserNotFoundException;
import com.mockrc8.app.global.oAuth.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping()
    public ResponseEntity<Object> getCommunityPosts(@RequestParam(required = false) Integer tagId){
        if(tagId == null){
            return communityService.getCommunityPosts();
        }
        return communityService.getPostsByTagId(tagId.longValue());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Object> getPostById(@PathVariable("postId") Integer postId){
        return communityService.getPostById(postId.longValue());

    }

    @GetMapping("/{postId}/comments")
    public BaseResponse<List<Comment>> getPostComments(@PathVariable("postId") Integer postId){
        return communityService.getPostComments(postId);
    }
    @GetMapping("/{postId}/likes")
    public BaseResponse<Integer> getPostLikesCount(@PathVariable("postId") Integer postId){
        return communityService.getPostLikesCount(postId);
    }

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

    @PostMapping("/{postId}/like")
    public ResponseEntity<Object> registerPostLike(@CurrentUser String userEmail,
                                                   @PathVariable("postId") Integer postId,
                                                   @RequestBody RegisterPostLikeDto registerPostLikeDto){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        registerPostLikeDto.setPost_id(postId.longValue());
        return communityService.registerPostLike(userEmail,registerPostLikeDto);
    }

    @GetMapping("/my/posts")
    public ResponseEntity<Object> getMyPosts(@CurrentUser String userEmail){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return communityService.getMyPosts(userEmail);
    }

    @GetMapping("/my/comments")
    public ResponseEntity<Object> getMyComments(@CurrentUser String userEmail){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return communityService.getMyComments(userEmail);
    }

    @GetMapping("/my/likes")
    public ResponseEntity<Object> getMyLikes(@CurrentUser String userEmail){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return communityService.getMyLikes(userEmail);
    }


}
