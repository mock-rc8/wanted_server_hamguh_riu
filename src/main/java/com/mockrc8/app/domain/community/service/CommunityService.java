package com.mockrc8.app.domain.community.service;

import com.mockrc8.app.domain.community.dto.RegisterPostCommentDto;
import com.mockrc8.app.domain.community.dto.RegisterPostReqDto;
import com.mockrc8.app.domain.community.mapper.CommunityMapper;
import com.mockrc8.app.domain.community.vo.Post;
import com.mockrc8.app.domain.community.vo.PostDetail;
import com.mockrc8.app.domain.community.vo.PostDetailWithComments;
import com.mockrc8.app.domain.user.service.UserService;
import com.mockrc8.app.domain.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityMapper communityMapper;
    private final UserService userService;
    public ResponseEntity<Object> getCommunityPosts() {
        final List<PostDetail> response =
                communityMapper.getCommunityPosts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Object> getPostsByTagId(Long tagId) {
        final List<PostDetail> response = communityMapper.getPostsByTagId(tagId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<Object> getPostById(Long postId) {
        final PostDetailWithComments response = communityMapper.getPostById(postId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    public Integer getPostlikeCount(Long postId){
        return communityMapper.getPostLikeCount(postId);
    }
    public Integer getPostCommentCount(Long postId){
        return communityMapper.getPostCommentCount(postId);
    }

    public ResponseEntity<Long> registerPost(String userEmail, RegisterPostReqDto registerPostReqDto) {
        final User user = userService.findUserByEmail(userEmail);
        registerPostReqDto.setUser_id(user.getUser_id());
        communityMapper.registerPost(registerPostReqDto);
        final Long postId = registerPostReqDto.getPost_id();
        registerPostTags(registerPostReqDto.getTagList(),postId);
        return new ResponseEntity<>(postId,HttpStatus.OK);
    }

    public void registerPostTags(List<Integer> tagList, Long postId){
        tagList.forEach(tagId -> communityMapper.registerPostTags(postId , tagId.longValue()));
    }

    public ResponseEntity<Long> registerPostComment(String userEmail, RegisterPostCommentDto commentDto){
        final User user = userService.findUserByEmail(userEmail);
        commentDto.setUser_id(user.getUser_id());
        communityMapper.registerPostComment(commentDto);
        final Long id = commentDto.getPost_comment_id();
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

//    public ResponseEntity<Object> getPostComments() {
//    }
//
//    public ResponseEntity<Object> getPostLikes() {
//    }



}
