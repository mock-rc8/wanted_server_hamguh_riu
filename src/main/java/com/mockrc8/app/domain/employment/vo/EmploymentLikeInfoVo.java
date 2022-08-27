package com.mockrc8.app.domain.employment.vo;


import com.mockrc8.app.domain.user.vo.UserProfileVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploymentLikeInfoVo {
    private Integer like_count;
    private List<UserProfileVo> userProfileVoList;
}
