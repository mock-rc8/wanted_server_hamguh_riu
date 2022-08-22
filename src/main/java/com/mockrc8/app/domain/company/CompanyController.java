package com.mockrc8.app.domain.company;


import com.mockrc8.app.domain.company.model.Company;
import com.mockrc8.app.domain.company.model.CompanyNews;
import com.mockrc8.app.domain.company.model.CompanyTag;
import com.mockrc8.app.domain.company.model.Image;
import com.mockrc8.app.domain.employment.EmploymentService;
import com.mockrc8.app.domain.employment.model.Employment;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private CompanyService companyService;

    private EmploymentService employmentService;


    @GetMapping()
    public ResponseEntity<List<Company>> getCompanyList(){
        List<Company> companyList = companyService.getCompanyList();

        return ResponseEntity.ok(companyList);
    }


    @GetMapping("/{companyId}")
    public ResponseEntity<Map<String, Object>> getCompanyById(@PathVariable Long companyId){
        Company company = companyService.getCompanyById(companyId);

        Map<String, Object> result = new HashMap<>();
        result.put(String.valueOf(company.getCompanyId()), company);

        List<Employment> employmentList = companyService.getEmploymentListByCompanyId(companyId);
        result.put("employment", employmentList);

        List<Image> companyImageList = companyService.getCompanyImageListByCompanyId(companyId);
        result.put("companyImage", companyImageList);

        List<CompanyTag> companyTagList = companyService.getCompanyTagListByCompanyId(companyId);
        result.put("companyTag", companyTagList);

        List<CompanyNews> companyNewsList = companyService.getCompanyNewsListByCompanyId(companyId);
        result.put("companyNews", companyNewsList);

        return ResponseEntity.ok(result);
    }


    @PostMapping()
    public ResponseEntity<Company> registerCompany(@RequestBody Company company){
        Long companyId = companyService.registerCompany(company);

        Company companyById = companyService.getCompanyById(companyId);

        return ResponseEntity.ok(companyById);
    }

}
