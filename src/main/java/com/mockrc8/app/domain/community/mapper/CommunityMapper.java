package com.mockrc8.app.domain.community.mapper;

import com.mockrc8.app.domain.community.dto.RegisterPostCommentDto;
import com.mockrc8.app.domain.community.dto.RegisterPostReqDto;
import com.mockrc8.app.domain.community.vo.Post;
import com.mockrc8.app.domain.community.vo.PostDetail;
import com.mockrc8.app.domain.community.vo.PostDetailWithComments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityMapper {
    List<PostDetail> getCommunityPosts();

    Integer getPostLikeCount(Long postId);

    Integer getPostCommentCount(Long postId);

    List<PostDetail> getPostsByTagId(Long tagId);

    PostDetailWithComments getPostById(Long postId);

    void registerPost(RegisterPostReqDto registerPostReqDto);

    void registerPostTags(@Param("postId") Long postId, @Param("tagId") Long tagId);

    void registerPostComment(RegisterPostCommentDto commentDto);
}
