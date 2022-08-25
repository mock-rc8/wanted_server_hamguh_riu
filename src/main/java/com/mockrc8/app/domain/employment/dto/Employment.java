package com.mockrc8.app.domain.employment.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class Employment {
    private Long employmentId;
    private Long companyId;
    private String title;
    private String context;
    private LocalDateTime deadline;
    private String country;
    private String location;
    private Integer referralCompensation;
    private Integer userCompensation;
    private Integer compensation;

    public Employment(Long companyId, String title, LocalDateTime deadline, Integer referralCompensation, Integer userCompensation){
        this.companyId = companyId;
        this.title = title;
        this.deadline = deadline;
        this.referralCompensation = referralCompensation;
        this.userCompensation = userCompensation;
    }

    public Employment(Long employmentId, String title, LocalDateTime deadline, Integer compensation){
        this.employmentId = employmentId;
        this.title = title;
        this.deadline = deadline;
        this.compensation = compensation;
    }

}
