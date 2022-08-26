package com.mockrc8.app.domain.resume.dto.Career;

import com.mockrc8.app.domain.resume.dto.Career.Career_accomplishmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareerDto {
    private Long career_id;
    private Long resume_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end_time;
    private String company_name;
    private String department_name;
    private int is_in_service = 0;
    private List<Career_accomplishmentDto> accomplishmentDtoList;
}
