package com.mockrc8.app.domain.community.service;

import com.mockrc8.app.domain.community.dto.GetUserCommentsDto;
import com.mockrc8.app.domain.community.dto.RegisterPostCommentDto;
import com.mockrc8.app.domain.community.dto.RegisterPostLikeDto;
import com.mockrc8.app.domain.community.dto.RegisterPostReqDto;
import com.mockrc8.app.domain.community.mapper.CommunityMapper;
import com.mockrc8.app.domain.community.vo.Comment;
import com.mockrc8.app.domain.community.vo.Post;
import com.mockrc8.app.domain.community.vo.PostDetail;
import com.mockrc8.app.domain.community.vo.PostDetailWithComments;
import com.mockrc8.app.domain.user.service.UserService;
import com.mockrc8.app.domain.user.vo.User;
import com.mockrc8.app.global.config.BaseResponse;
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

    public BaseResponse<List<Comment>> getPostComments(Integer postId) {
        final PostDetailWithComments postById = communityMapper.getPostById(postId.longValue());
        final List<Comment> commentList = postById.getCommentList();
        return new BaseResponse<>("게시글 댓글 가져오기 요청에 성공했습니다",commentList);
    }

    public BaseResponse<Integer> getPostLikesCount(Integer postId) {
        final Integer postLikeCount = communityMapper.getPostLikeCount(postId.longValue());
        return new BaseResponse<>("게시글 좋아요 수 가져오기 요청에 성공했습니다.",postLikeCount);
    }

    public ResponseEntity<Object> registerPostLike(String userEmail, RegisterPostLikeDto registerPostLikeDto) {
        final User user = userService.findUserByEmail(userEmail);
        registerPostLikeDto.setUser_id(user.getUser_id());
        communityMapper.registerPostLike(registerPostLikeDto);
        final BaseResponse<RegisterPostLikeDto> baseResponse = new BaseResponse<>("게시글 좋아요 요청에 성공했습니다.", registerPostLikeDto);
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);

    }

    public ResponseEntity<Object> getMyPosts(String userEmail) {
        final User user = userService.findUserByEmail(userEmail);
        final List<PostDetail> postByUserId = communityMapper.getPostByUserId(user.getUser_id());
        final BaseResponse<List<PostDetail>> baseResponse = new BaseResponse<>("유저의 게시글 가져오기 요청에 성공했습니다.", postByUserId);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    public ResponseEntity<Object> getMyComments(String userEmail) {
        final User user = userService.findUserByEmail(userEmail);
        final GetUserCommentsDto myComments = communityMapper.getMyComments(user.getUser_id());
        final BaseResponse<GetUserCommentsDto> baseResponse = new BaseResponse<>("유저의 게시글 댓글 가져오기 요청에 성공했습니다.", myComments);
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }


    public ResponseEntity<Object> getMyLikes(String userEmail) {
        final User user = userService.findUserByEmail(userEmail);
        final List<Post> myLikePost = communityMapper.getMyLikes(user.getUser_id());
        final BaseResponse<List<Post>> baseResponse = new BaseResponse<>("좋아요한 게시글 가져오기 요청에 성공했습니다.", myLikePost);
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }
}
