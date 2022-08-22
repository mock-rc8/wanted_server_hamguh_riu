package com.mockrc8.app.domain.employment;

import com.mockrc8.app.domain.company.CompanyService;
import com.mockrc8.app.domain.company.model.Company;
import com.mockrc8.app.domain.company.model.CompanyTag;
import com.mockrc8.app.domain.company.model.Image;
import com.mockrc8.app.domain.employment.model.Employment;
import com.mockrc8.app.domain.employment.model.TechSkill;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<Image> imageList = employmentService.getCompanyImageListByCompanyId(employmentId);
        result.put("image", imageList);


        // company_tag
        List<CompanyTag> companyTagList = companyService.getCompanyTagListByCompanyId(employment.getCompanyId());
        result.put("companyTag", companyTagList);

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


        // 관련 employment 목록


        return ResponseEntity.ok(result);
    }
}
