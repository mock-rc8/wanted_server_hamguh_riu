package com.mockrc8.app.domain.resume.mapper;

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
}
