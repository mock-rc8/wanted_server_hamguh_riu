package com.mockrc8.app.domain.community.dto;

import com.mockrc8.app.domain.community.vo.CommentWithPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserCommentsDto {
    private Long user_id;
    private List<CommentWithPost> comments;
}
