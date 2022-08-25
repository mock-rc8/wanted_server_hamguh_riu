package com.mockrc8.app.domain.employment.controller;

import com.mockrc8.app.domain.company.service.CompanyService;
import com.mockrc8.app.domain.company.dto.Company;
import com.mockrc8.app.domain.company.dto.CompanyTag;
import com.mockrc8.app.domain.company.dto.Image;
import com.mockrc8.app.domain.employment.service.EmploymentService;
import com.mockrc8.app.domain.employment.dto.Employment;
import com.mockrc8.app.domain.employment.dto.TechSkill;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/employment")
@AllArgsConstructor
public class EmploymentController {

    private EmploymentService employmentService;

    private CompanyService companyService;

    @GetMapping()
    public ResponseEntity<List<Employment>> getEmploymentList(){
        List<Employment> employmentList = employmentService.getEmploymentList();

        return ResponseEntity.ok(employmentList);
    }


    /*
    채용 ID로 특정 채용 조회 API
     */

    @GetMapping("/{employmentId}")
    public ResponseEntity<Map<String, Object>> getEmploymentById(@PathVariable Long employmentId){
        Employment employment = employmentService.getEmploymentById(employmentId);
        Long companyId = employment.getCompanyId();

        // employment
        Map<String, Object> result = new HashMap<>();
        result.put("employment", employment);


        //company
        Company company = companyService.getCompanyById(companyId);
        result.put("company", company);


        // employment_image
        List<Image> imageList = employmentService.getEmploymentImageListByCompanyId(employmentId);
        result.put("image", imageList);


        // company_tag
        List<CompanyTag> companyTagList = companyService.getCompanyTagListByCompanyId(employment.getCompanyId());
        result.put("companyTag", companyTagList);

        // 현재 조회중인 채용 공고를 등록한 회사의 태그 리스트를 가지고,
        // 반복자를 통해 해당 태그를 가지는 채용 공고들을 최대 16개까지 조회해야 한다.
        // 현재 조회중인 채용 공고는 제외해야 한다.
        Iterator<CompanyTag> it = companyTagList.iterator();

        List<Employment> employmentList = new ArrayList<>();
        Integer maxCount = 16;
        Integer count = 0;

        while(it.hasNext()){
            CompanyTag companyTag = it.next();
            count = maxCount - count;
            employmentList.addAll(employmentService.getEmploymentListByCompanyTagName(companyTag.getCompanyTagName(), employmentId, maxCount));

            if(employmentList.size() == maxCount){
                break;
            }
        }
        result.put("associatedEmployment", employmentList);


        // employment_tech_skill
        List<TechSkill> employmentTechSkillList = employmentService.getEmploymentTechSkillListByEmploymentId(employmentId);
        result.put("techSkill", employmentTechSkillList);


        return ResponseEntity.ok(result);
    }


    @GetMapping("themes/incentive")
    public ResponseEntity<Object> getEmploymentListByCompensation(HttpServletRequest request){
        String lastSelectedEmploymentString = request.getHeader("lastSelectedEmploymentId");
        Integer lastSelectedEmploymentId = null;
        if(lastSelectedEmploymentString != null){
            lastSelectedEmploymentId = Integer.valueOf(lastSelectedEmploymentString);
        }
        return employmentService.getEmploymentListByCompensation(lastSelectedEmploymentId);
    }


    @GetMapping("themes/closesoon")
    public ResponseEntity<Object> getEmploymentListByCloseSoon(HttpServletRequest request){

        String lastSelectedEmploymentString = request.getHeader("lastSelectedEmploymentId");
        Integer lastSelectedEmploymentId = null;
        if(lastSelectedEmploymentString != null){
            lastSelectedEmploymentId = Integer.valueOf(lastSelectedEmploymentString);
        }
        return employmentService.getEmploymentListByCloseSoon(lastSelectedEmploymentId);
    }
}
