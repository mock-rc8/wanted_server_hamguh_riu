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
public class Resume_education_degree {
    private Long resume_education_degree_id;
    private Long resume_id;
    private LocalDate start_time;
    private LocalDate end_time;
    private int is_in_service;
    private String degree_name;
    private String content;
}
