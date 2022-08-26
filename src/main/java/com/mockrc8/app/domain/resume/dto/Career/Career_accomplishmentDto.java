package com.mockrc8.app.domain.resume.dto.Career;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Career_accomplishmentDto {
    private Long career_id;
    private String title;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end_time;

}
