package com.mockrc8.app.domain.resume.mapper;

import com.mockrc8.app.domain.resume.dto.Award.AwardDto;
import com.mockrc8.app.domain.resume.dto.Award.AwardListDto;
import com.mockrc8.app.domain.resume.dto.Career.CareerDto;
import com.mockrc8.app.domain.resume.dto.Career.Career_accomplishmentDto;
import com.mockrc8.app.domain.resume.dto.Degree.DegreeDto;
import com.mockrc8.app.domain.resume.dto.Language.Language_skillDto;
import com.mockrc8.app.domain.resume.dto.Language.Language_testDto;
import com.mockrc8.app.domain.resume.dto.TechSkill.PostResume_tech_skillDto;
import com.mockrc8.app.domain.resume.dto.file.PostResumeFileDto;
import com.mockrc8.app.domain.resume.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResumeMapper {
    List<Resume> getResumesByUserId(Long userId);

//    ResumeDetail getUserResumeByResumeId(@Param("userId") Long userId, @Param("resumeId") Integer resumeId);

    Resume getResumeById(@Param("resumeId") Integer resumeId);

    List<Resume_award> getAward(@Param("resumeId") Integer resumeId);

    List<Resume_career> getCareer(@Param("resumeId") Integer resumeId);

    List<Resume_education_degree> getDegree(@Param("resumeId") Integer resumeId);

    List<Resume_tech_skill> getTechSkills(@Param("resumeId") Integer resumeId);

    List<Resume_language_skill> getLanguage(@Param("resumeId") Integer resumeId);

    void postResumeCareer(CareerDto dto);

    void postResumeCareer_accomplishment(List<Career_accomplishmentDto> dto);

    void postResumeAward(AwardDto dto);

    void postResumeDegree(DegreeDto degreeDto);

    void postLanguageSkill(Language_skillDto dto);

    void postResumeLanguageTest(List<Language_testDto> dto);

    Long getTechSkillId(String skillName);

    void postResumeTechSkills(@Param("resumeId") Long resumeId, @Param("resumeTechSkillId") Long resumeTechSkillId);

    void postResumeFile(PostResumeFileDto postResumeFileDto);

    ResumeWithLink findResumeByResourcePath(String resourcePath);

    void patchResumeCareer(CareerDto dto);

    void patchResumeAward(AwardDto dto);
}
