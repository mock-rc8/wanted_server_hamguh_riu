package com.mockrc8.app.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPostUserResDto {
    private Long user_id;
    private String profile_image;
}
