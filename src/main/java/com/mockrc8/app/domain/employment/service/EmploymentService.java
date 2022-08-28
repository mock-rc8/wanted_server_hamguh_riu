package com.mockrc8.app.domain.employment.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mockrc8.app.domain.company.dto.Image;
import com.mockrc8.app.domain.company.mapper.CompanyMapper;
import com.mockrc8.app.domain.employment.mapper.EmploymentMapper;
import com.mockrc8.app.domain.employment.dto.Employment;
import com.mockrc8.app.domain.employment.dto.EmploymentImage;
import com.mockrc8.app.domain.employment.dto.EmploymentTechSkill;
import com.mockrc8.app.domain.employment.dto.TechSkill;
import com.mockrc8.app.domain.employment.vo.EmploymentLikeInfoVo;
import com.mockrc8.app.domain.employment.vo.ReducedEmploymentVo;
import com.mockrc8.app.domain.user.mapper.UserMapper;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.exception.company.ImageNotExistException;
import com.mockrc8.app.global.error.exception.employment.EmploymentNotExistException;
import com.mockrc8.app.global.error.exception.employment.EmploymentTechSkillNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.mockrc8.app.global.error.ErrorCode.*;

@Service
@AllArgsConstructor
public class EmploymentService {

    private CompanyMapper companyMapper;
    private EmploymentMapper employmentMapper;
    private UserMapper userMapper;


    // 채용 목록 전체 조회
    public List<ReducedEmploymentVo> getEmploymentList(Long jobGroupId, Long detailedJobGroupId){
        return employmentMapper.getEmploymentList(jobGroupId, detailedJobGroupId);
    }

    // 직군를 가지고 채용 목록 조회
    public List<ReducedEmploymentVo> getEmploymentListByJobGroup(Long jobGroupId){
        return employmentMapper.getEmploymentListByJobGroup(jobGroupId);
    }

    // 직군, 직무를 가지고 채용 목록 조회
    public List<ReducedEmploymentVo> getEmploymentListByDetailedJobGroup(Long jobGroupId, Long detailedJobGroupId){
        return employmentMapper.getEmploymentListByDetailedJobGroup(jobGroupId, detailedJobGroupId);
    }

    // 축약된 속성을 지닌 채용 목록을 회사 id로 조회
//    public List<Employment> getReducedEmploymentListByCompanyId(Long companyId){
//        return employmentMapper.getReducedEmploymentListByCompanyId(companyId);
//    }

    // 태그명으로 채용목록 조회
    public List<Employment> getEmploymentListByCompanyTagName(String companyTagName, Long employmentId){
        return employmentMapper.getEmploymentListByCompanyTagName(companyTagName, employmentId);
    }


    // 성과급, 상여금, 인센티브 ... 태그 조회
    public ResponseEntity<Object> getEmploymentListByTagNames(String[] tagNames, Integer scrollCount){
        PageHelper.startPage(scrollCount, 10);
        List<ReducedEmploymentVo> reducedEmploymentVoList = employmentMapper.getEmploymentListByTagNames(tagNames);

        BaseResponse<List<ReducedEmploymentVo>> response = new BaseResponse<>(reducedEmploymentVoList);
        return ResponseEntity.ok(response);
    }

    // 채용 마감 임박 테마
    public ResponseEntity<Object> getEmploymentListByCloseSoon(Integer scrollCount){
        PageHelper.startPage(scrollCount, 10);
        List<ReducedEmploymentVo> reducedEmploymentVoList = employmentMapper.getEmploymentListByCloseSoon();

        BaseResponse<List<ReducedEmploymentVo>> response = new BaseResponse<>(reducedEmploymentVoList);
        return ResponseEntity.ok(response);
    }

    // 신입 채용 테마
    public ResponseEntity<Object> getEmploymentListByNewcomer(Integer scrollCount){
        PageHelper.startPage(scrollCount, 10);
        List<ReducedEmploymentVo> reducedEmploymentVoList = employmentMapper.getEmploymentListByCareerYear(0, 0);

        BaseResponse<List<ReducedEmploymentVo>> response = new BaseResponse<>(reducedEmploymentVoList);
        return ResponseEntity.ok(response);
    }


    // 페이지네이션 적용하지 않았음. 이후에 적용
    public List<ReducedEmploymentVo> getEmploymentListByCareerYear(Integer minYear, Integer maxYear){

        return employmentMapper.getEmploymentListByCareerYear(minYear, maxYear);

    }

    /*
    특정 id로만 조회할 때, 존재하지 않는다면 exception
     */

    public Employment getEmploymentById(Long employmentId){
        if(employmentMapper.checkEmploymentId(employmentId) == 0){
            throw new EmploymentNotExistException(EMPLOYMENT_NOT_EXIST);
        }
        return employmentMapper.getEmploymentById(employmentId);
    }





    /*
    company_image
     */


//    public Image getEmploymentImageByEmploymentId(Long employmentId){
//        EmploymentImage employmentImage = employmentMapper.getEmploymentImageByEmploymentId(employmentId);
//
//        Long imageId = employmentImage.getImageId();
//        if(companyMapper.checkImageId(imageId) == 0){
//            throw new ImageNotExistException(IMAGE_NOT_EXIST);
//        }
//
//        return companyMapper.getImageById(imageId);
//    }

    public List<Image> getEmploymentImageListByEmploymentId(Long employmentId){
        List<EmploymentImage> employmentImageList = employmentMapper.getEmploymentImageListByEmploymentId(employmentId);


        Iterator<EmploymentImage> it = employmentImageList.iterator();

        List<Image> imageList = new ArrayList<>();

        while(it.hasNext()){
            EmploymentImage employmentImage = it.next();
            Long imageId = employmentImage.getImageId();

            if(companyMapper.checkImageId(imageId) == 0){
                throw new ImageNotExistException(IMAGE_NOT_EXIST);
            }
            imageList.add(companyMapper.getImageById(imageId));
        }

        return imageList;
    }


    /*
    employment_tech_skill
     */

    public List<TechSkill> getEmploymentTechSkillListByEmploymentId(Long employmentId){

//        if(employmentMapper.checkEmploymentTechSkillByEmploymentId(employmentId) == 0){
//            throw new EmploymentTechSkillNotExistException(EMPLOYMENT_TECH_SKILL_NOT_EXIST);
//        }
        List<EmploymentTechSkill> employmentTechSkillList = employmentMapper.getEmploymentTechSkillListByEmploymentId(employmentId);

        Iterator<EmploymentTechSkill> it = employmentTechSkillList.iterator();

        List<TechSkill> techSkillList = new ArrayList<>();

        while(it.hasNext()){
            EmploymentTechSkill employmentTechSkill = it.next();

            TechSkill techSkill = employmentMapper.getTechSkillById(employmentTechSkill.getTechSkillId());
            techSkillList.add(techSkill);
        }

        return techSkillList;
    }

    public EmploymentLikeInfoVo getEmploymentLikeInfoVo(Long employmentId){
        return employmentMapper.getEmploymentLikeInfoVo(employmentId);
    }





    // ReducedEmployment를 employmentId로 조회
    public ReducedEmploymentVo getReducedEmploymentByEmploymentId(Long employmentId){
        if(employmentMapper.checkEmploymentId(employmentId) == 0){
            throw new EmploymentNotExistException(EMPLOYMENT_NOT_EXIST);
        }
        return employmentMapper.getReducedEmploymentByEmploymentId(employmentId);
    }
}
