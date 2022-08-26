package com.mockrc8.app.domain.resume.dto.Language;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language_testDto {
    private Long resume_language_test_id;
    private Long resume_language_skill_id;
    private String test_name;
    private Integer score;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate acquisition_date;
}
