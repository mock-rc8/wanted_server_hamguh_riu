package com.mockrc8.app.domain.employment.mapper;

import com.mockrc8.app.domain.company.dto.Image;
import com.mockrc8.app.domain.employment.dto.Employment;
import com.mockrc8.app.domain.employment.dto.EmploymentImage;
import com.mockrc8.app.domain.employment.dto.EmploymentTechSkill;
import com.mockrc8.app.domain.employment.dto.TechSkill;
import com.mockrc8.app.domain.employment.vo.ReducedEmploymentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmploymentMapper {

    Integer checkEmploymentId(Long employmentId);
    List<Employment> getEmploymentListByCompanyTagName(Map<String, Object> map);
    List<Employment> getReducedEmploymentListByCompanyId(@Param("companyId") Long companyId);
    List<Employment> getEmploymentList();
    Employment getEmploymentById(Long employmentId);


    //employment_image
    List<EmploymentImage> getEmploymentImageListByEmploymentId(Long employmentId);
    EmploymentImage getEmploymentImageByEmploymentId(Long employmentId);

    //employment_tech_skill
//    Integer checkEmploymentTechSkillByEmploymentId(Long employmentId);
    List<EmploymentTechSkill> getEmploymentTechSkillListByEmploymentId(Long employmentId);

    //tech_skill
    TechSkill getTechSkillById(Long techSkillId);

    ReducedEmploymentVo getReducedEmploymentByEmploymentId(Long employmentId, Boolean needCompanyImage);
    List<ReducedEmploymentVo> getEmploymentListByCompensation(Integer lastSelectedEmploymentId);
    List<ReducedEmploymentVo> getEmploymentListByCloseSoon(Integer lastSelectedEmploymentId);
}
