package com.mockrc8.app.domain.community.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResDto {
    private Long post_id;
    private Long user_id;
    private String title;
    private String content;
    private LocalDateTime created_at;


}
