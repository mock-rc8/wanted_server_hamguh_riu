package com.mockrc8.app.domain.employment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechSkill {
    private Long techSkillId;
    private String techSkillName;

    public TechSkill(String techSkillName){
        this.techSkillName = techSkillName;
    }
}
