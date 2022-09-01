package com.mockrc8.app.domain.user.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class UserExcludedCompanyDto {
//    private Long user_excluded_company_id;
    private Long user_id;
    private Long company_id;

    public UserExcludedCompanyDto(Long user_id, Long company_id){
        this.user_id = user_id;
        this.company_id = company_id;
    }
}
