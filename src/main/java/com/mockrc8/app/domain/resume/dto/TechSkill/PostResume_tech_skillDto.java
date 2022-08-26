package com.mockrc8.app.domain.resume.dto.TechSkill;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResume_tech_skillDto {
    private Long resume_id;
    private List<Long> techSkillIdList;
}
