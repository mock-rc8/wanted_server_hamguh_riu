package com.mockrc8.app.domain.resume.dto.TechSkill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resume_tech_skillDto {
    private Long resume_skill_id;
    private Long resume_id;
    private List<String> skillName;
}
