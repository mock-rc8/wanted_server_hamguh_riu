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
public class Resume_language_test {
    private Long resume_language_test_id;
    private Long resume_language_skill_id;
    private String test_name;
    private int score;
    private LocalDate acquisition_date;
}
