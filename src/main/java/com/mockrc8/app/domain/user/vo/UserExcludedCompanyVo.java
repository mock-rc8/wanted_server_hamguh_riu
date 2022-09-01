package com.mockrc8.app.domain.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class UserExcludedCompanyVo {
    private Long user_excluded_company_id;
    private Long user_id;
    private Long company_id;
    private String name;
}
