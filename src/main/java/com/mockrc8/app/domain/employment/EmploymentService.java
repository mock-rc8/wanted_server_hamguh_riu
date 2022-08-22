package com.mockrc8.app.domain.employment;

import com.mockrc8.app.domain.company.CompanyDao;
import com.mockrc8.app.domain.company.model.CompanyImage;
import com.mockrc8.app.domain.company.model.Image;
import com.mockrc8.app.domain.employment.model.Employment;
import com.mockrc8.app.domain.employment.model.EmploymentImage;
import com.mockrc8.app.domain.employment.model.EmploymentTechSkill;
import com.mockrc8.app.domain.employment.model.TechSkill;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.company.ImageNotExistException;
import com.mockrc8.app.global.error.exception.employment.EmploymentNotExistException;
import com.mockrc8.app.global.error.exception.employment.EmploymentTechSkillNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mockrc8.app.global.error.ErrorCode.*;

@Service
@AllArgsConstructor
public class EmploymentService {

    private EmploymentDao employmentDao;
    private CompanyDao companyDao;

    public List<Employment> getEmploymentList(){
        return employmentDao.getEmploymentList();
    }


    public List<Employment> getEmploymentListByCompanyId(Long companyId){
        return employmentDao.getEmploymentListByCompanyId(companyId);
    }


    public List<Employment> getEmploymentListByCompanyTagName(String companyTagName, Long employmentId, Integer count){
        return employmentDao.getEmploymentListByCompanyTagName(companyTagName, employmentId, count);
    }


    /*
    특정 id로만 조회할 때, 존재하지 않는다면 exception
     */

    public Employment getEmploymentById(Long employmentId){
        if(employmentDao.checkEmploymentId(employmentId) == 0){
            throw new EmploymentNotExistException(EMPLOYMENT_NOT_EXIST);
        }
        return employmentDao.getEmploymentById(employmentId);
    }


    public Long registerEmployment(Employment employment){
        Long employmentId = employmentDao.registerEmployment(employment);

        // dao에서 생성 후 받은 키값을 확인
        if(employmentDao.checkEmploymentId(employmentId) == 0){
            throw new EmploymentNotExistException(EMPLOYMENT_NOT_EXIST);
        }

        return employmentId;
    }



    /*
    company_image
     */

    public List<Image> getCompanyImageListByCompanyId(Long employmentId){
        List<EmploymentImage> employmentImageList = employmentDao.getEmploymentImageListByCompanyId(employmentId);


        Iterator<EmploymentImage> it = employmentImageList.iterator();

        List<Image> imageList = new ArrayList<>();

        while(it.hasNext()){
            EmploymentImage employmentImage = it.next();
            Long imageId = employmentImage.getImageId();

            if(companyDao.checkImageId(imageId) == 0){
                throw new ImageNotExistException(IMAGE_NOT_EXIST);
            }
            imageList.add(companyDao.getImageById(imageId));
        }

        return imageList;
    }


    /*
    employment_tech_skill
     */

    public List<TechSkill> getEmploymentTechSkillListByEmploymentId(Long employmentId){

        if(employmentDao.checkEmploymentTechSkillByEmploymentId(employmentId) == 0){
            throw new EmploymentTechSkillNotExistException(EMPLOYMENT_TECH_SKILL_NOT_EXIST);
        }
        List<EmploymentTechSkill> employmentTechSkillList = employmentDao.getEmploymentTechSkillListByEmploymentId(employmentId);

        Iterator<EmploymentTechSkill> it = employmentTechSkillList.iterator();

        List<TechSkill> techSkillList = new ArrayList<>();

        while(it.hasNext()){
            EmploymentTechSkill employmentTechSkill = it.next();

            TechSkill techSkill = employmentDao.getTechSkillById(employmentTechSkill.getTechSkillId());
            techSkillList.add(techSkill);
        }

        return techSkillList;
    }
}
