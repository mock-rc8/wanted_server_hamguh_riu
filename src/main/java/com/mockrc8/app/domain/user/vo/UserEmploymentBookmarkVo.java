package com.mockrc8.app.domain.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEmploymentBookmarkVo {
    private Long user_employment_bookmark_id;
    private Long user_id;
    private Long employment_id;
}
