package com.mockrc8.app.domain.employment.service;


import com.mockrc8.app.domain.company.dto.Image;
import com.mockrc8.app.domain.company.mapper.CompanyMapper;
import com.mockrc8.app.domain.employment.mapper.EmploymentMapper;
import com.mockrc8.app.domain.employment.dto.Employment;
import com.mockrc8.app.domain.employment.dto.EmploymentImage;
import com.mockrc8.app.domain.employment.dto.EmploymentTechSkill;
import com.mockrc8.app.domain.employment.dto.TechSkill;
import com.mockrc8.app.domain.employment.vo.ReducedEmploymentVo;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.exception.company.ImageNotExistException;
import com.mockrc8.app.global.error.exception.employment.EmploymentNotExistException;
import com.mockrc8.app.global.error.exception.employment.EmploymentTechSkillNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.mockrc8.app.global.error.ErrorCode.*;

@Service
@AllArgsConstructor
public class EmploymentService {

    private CompanyMapper companyMapper;
    private EmploymentMapper employmentMapper;


    // 채용 목록 전체 조회
    public List<Employment> getEmploymentList(){
        return employmentMapper.getEmploymentList();
    }

    // 축약된 속성을 지닌 채용 목록을 회사 id로 조회
    public List<Employment> getReducedEmploymentListByCompanyId(Long companyId){
        return employmentMapper.getReducedEmploymentListByCompanyId(companyId);
    }

    // 태그명으로 채용목록 조회
    public List<Employment> getEmploymentListByCompanyTagName(String companyTagName, Long employmentId, Integer count){

        companyTagName = "%" + companyTagName + "%";
        Map<String, Object> map = new HashMap<>();
        map.put("companyTagName", companyTagName);
        map.put("employmentId", employmentId);
        map.put("count", count);

        return employmentMapper.getEmploymentListByCompanyTagName(map);
    }


    // 성과급, 상여금, 인센티브 태그 조회
    public ResponseEntity<Object> getEmploymentListByCompensation(Integer lastSelectedEmploymentId){
        List<ReducedEmploymentVo> reducedEmploymentVoList = employmentMapper.getEmploymentListByCompensation(lastSelectedEmploymentId);

        BaseResponse<List<ReducedEmploymentVo>> response = new BaseResponse<>(reducedEmploymentVoList);
        return ResponseEntity.ok(response);
    }

    // 채용 마감 임박
    public ResponseEntity<Object> getEmploymentListByCloseSoon(Integer lastSelectedEmploymentId){
        List<ReducedEmploymentVo> reducedEmploymentVoList = employmentMapper.getEmploymentListByCloseSoon(lastSelectedEmploymentId);

        BaseResponse<List<ReducedEmploymentVo>> response = new BaseResponse<>(reducedEmploymentVoList);
        return ResponseEntity.ok(response);
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

    public List<Image> getEmploymentImageListByCompanyId(Long employmentId){
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


    // ReducedEmployment를 employmentId로 조회
//    ReducedEmploymentVo getReducedEmploymentByEmploymentId(Long employmentId){
//        if(employmentMapper.checkEmploymentId(employmentId) == 0){
//            throw new EmploymentNotExistException(EMPLOYMENT_NOT_EXIST);
//        }
//        return employmentMapper.getReducedEmploymentByEmploymentId(employmentId);
//    }
}
