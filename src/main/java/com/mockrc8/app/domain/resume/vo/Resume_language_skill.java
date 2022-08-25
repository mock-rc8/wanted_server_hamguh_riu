package com.mockrc8.app.domain.resume.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume_language_skill {
    private Long resume_language_skill_id;
    private Long resume_id;
    private String language;
    private String level;
    private List<Resume_language_test> tests;
}
