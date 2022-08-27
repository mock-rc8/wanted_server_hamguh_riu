package com.mockrc8.app.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class UserCompanyFollowDto {
    private Long user_company_follow_id;
    private Long user_id;
    private Long company_id;

    public UserCompanyFollowDto(Long user_id, Long company_id){
        this.user_id = user_id;
        this.company_id = company_id;
    }
}
