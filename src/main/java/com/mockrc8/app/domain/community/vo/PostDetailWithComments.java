package com.mockrc8.app.domain.community.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailWithComments {
    private Long post_id;
    private Post post;
    private List<PostTag> tagList;
    private List<Comment> commentList;
}
