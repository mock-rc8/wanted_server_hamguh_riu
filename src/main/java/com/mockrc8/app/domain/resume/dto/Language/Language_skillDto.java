package com.mockrc8.app.domain.resume.dto.Language;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language_skillDto {
    private Long resume_language_skill_id;
    private Long resume_id;
    private String language;
    private String level;
    private List<Language_testDto> languageTest;
}
