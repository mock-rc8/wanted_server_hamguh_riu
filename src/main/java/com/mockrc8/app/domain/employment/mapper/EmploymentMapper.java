package com.mockrc8.app.domain.employment.mapper;

import com.github.pagehelper.Page;
import com.mockrc8.app.domain.company.dto.Image;
import com.mockrc8.app.domain.employment.dto.Employment;
import com.mockrc8.app.domain.employment.dto.EmploymentImage;
import com.mockrc8.app.domain.employment.dto.EmploymentTechSkill;
import com.mockrc8.app.domain.employment.dto.TechSkill;
import com.mockrc8.app.domain.employment.vo.EmploymentLikeInfoVo;
import com.mockrc8.app.domain.employment.vo.ReducedEmploymentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmploymentMapper {

    Integer checkEmploymentId(Long employmentId);
    List<Employment> getEmploymentListByCompanyTagName(String companyTagName, Long employmentId);
    List<Employment> getReducedEmploymentListByCompanyId(Long companyId);
    Page<ReducedEmploymentVo> getEmploymentList(Long jobGroupId, Long detailedJobGroupId, String sort);
//    Page<ReducedEmploymentVo> getEmploymentList(Long jobGroupId, Long detailedJobGroupId);
    Page<ReducedEmploymentVo> getEmploymentListByJobGroup(Long jobGroupId);
    Page<ReducedEmploymentVo> getEmploymentListByDetailedJobGroup(Long jobGroupId, Long detailedJobGroupId);

    Employment getEmploymentById(Long employmentId);

    EmploymentLikeInfoVo getEmploymentLikeInfoVo(Long employmentId);

    void raiseViewCount(Long employmentId);

    //employment_image
    List<EmploymentImage> getEmploymentImageListByEmploymentId(Long employmentId);
    EmploymentImage getEmploymentImageByEmploymentId(Long employmentId);

    //employment_tech_skill
    List<EmploymentTechSkill> getEmploymentTechSkillListByEmploymentId(Long employmentId);

    //tech_skill
    TechSkill getTechSkillById(Long techSkillId);

    ReducedEmploymentVo getReducedEmploymentByEmploymentId(Long employmentId);
    Page<ReducedEmploymentVo> getEmploymentListByTagNames(String[] tagNames);
    Page<ReducedEmploymentVo> getEmploymentListByCloseSoon();
    List<ReducedEmploymentVo> getEmploymentListByNationId(Long nationId);
    Page<ReducedEmploymentVo> getEmploymentListByCareerYear(Integer minYear, Integer maxYear);
    Page<ReducedEmploymentVo> getEmploymentListByTechSkill(Long[] techSkillId);
}
