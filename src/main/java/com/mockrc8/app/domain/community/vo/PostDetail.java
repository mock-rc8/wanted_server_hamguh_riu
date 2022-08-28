package com.mockrc8.app.domain.community.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDetail {
    private Long post_id;
    private Long user_id;
    private Integer commentCount;
    private Integer likeCount;
    private String profile_image;
    private Post post;
    private List<PostTag> tagList;
}
