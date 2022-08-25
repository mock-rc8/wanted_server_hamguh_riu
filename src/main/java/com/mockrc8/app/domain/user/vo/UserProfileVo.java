package com.mockrc8.app.domain.user.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class UserProfileVo {
    private Long user_id;
    private String name;
    private String email;
    private String phone_number;
    private String profile_image;

//    private List<UserEmploymentBookmarkVo> userEmploymentBookmarkVoList;
//    private List<UserEmploymentLikeVo> userEmploymentLikeVoList;
}
