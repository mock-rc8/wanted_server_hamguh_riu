package com.mockrc8.app.domain.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEmploymentLikeVo {
    private Long user_employment_like_id;
    private Long user_id;
    private Long employment_id;
}
