package com.mockrc8.app.domain.community.dto;

import com.mockrc8.app.domain.community.vo.PostTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPostReqDto {
    private Long post_id;
    private Long user_id;
    private String title;
    private String content;
    private List<Integer> tagList;
}
