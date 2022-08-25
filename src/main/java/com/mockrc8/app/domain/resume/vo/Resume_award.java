package com.mockrc8.app.domain.resume.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume_award {
    private Long resume_award_id;
    private Long resume_id;
    private LocalDate award_date;
    private String award_title;
    private String award_detail;
}
