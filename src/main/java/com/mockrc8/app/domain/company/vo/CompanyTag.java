package com.mockrc8.app.domain.company.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
@Builder
public class CompanyTag {
    private Long hashtag_id;
    private Long company_id;
    private String hashtag_name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
