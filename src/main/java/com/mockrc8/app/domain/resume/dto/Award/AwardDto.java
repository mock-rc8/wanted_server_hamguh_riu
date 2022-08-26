package com.mockrc8.app.domain.resume.dto.Award;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwardDto {
    private Long resume_award_id;
    private Long resume_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate award_date;
    private String award_title;
    private String award_detail;
}
