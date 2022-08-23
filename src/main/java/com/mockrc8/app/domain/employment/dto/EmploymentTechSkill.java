package com.mockrc8.app.domain.employment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentTechSkill {
    private Long employmentTechSkillId;
    private Long employmentId;
    private Long techSkillId;

    public EmploymentTechSkill(Long employmentId, Long techSkillId){
        this.employmentId = employmentId;
        this.techSkillId = techSkillId;
    }
}
