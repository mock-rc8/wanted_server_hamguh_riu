package com.mockrc8.app.domain.employment.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class EmploymentVo {
    private Long employment_id;
    private Long company_id;
    private String title;
    private String context;
    private LocalDate deadline;
    private String country;
    private String location;
    private int referral_compensation;
    private int user_compensation;
}