package com.mockrc8.app.domain.resume.dto.Degree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DegreeDto {
    private Long resume_education_degree_id;
    private Long resume_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end_time;
    private int is_in_service = 0;
    private String degree_name;
    private String content;
}
