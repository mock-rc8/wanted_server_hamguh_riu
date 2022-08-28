package com.mockrc8.app.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPostCommentDto {
    private Long post_comment_id;
    private Long user_id;
    private Long post_id;
    private String comment;
}
