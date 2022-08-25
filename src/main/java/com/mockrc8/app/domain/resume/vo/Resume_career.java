package com.mockrc8.app.domain.resume.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume_career {
    private Long career_id;
    private Long resume_id;
    private LocalDate start_time;
    private LocalDate end_time;
    private String company_name;
    private String department_name;
    private int is_in_service;
    private List<Resume_career_accomplishment> accomplishmentList;
}
