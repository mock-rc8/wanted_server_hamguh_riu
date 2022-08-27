package com.mockrc8.app.domain.community.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Long post_id;
    private Long user_id;
    private String title;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
