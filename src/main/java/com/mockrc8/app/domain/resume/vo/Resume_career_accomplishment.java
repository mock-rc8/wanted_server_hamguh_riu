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
public class Resume_career_accomplishment {
    private Long career_accomplishment_id;
    private Long career_id;
    private String title;
    private String description;
    private LocalDate start_time;
    private LocalDate end_time;
}
