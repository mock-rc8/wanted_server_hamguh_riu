package com.mockrc8.app.domain.company.controller;


import com.mockrc8.app.domain.company.service.CompanyService;
import com.mockrc8.app.domain.company.vo.Company;
import com.mockrc8.app.domain.company.dto.CompanyNews;
import com.mockrc8.app.domain.company.dto.Image;
import com.mockrc8.app.domain.company.vo.CompanyInfo;
import com.mockrc8.app.domain.company.vo.CompanyTag;
import com.mockrc8.app.domain.employment.service.EmploymentService;
import com.mockrc8.app.domain.employment.vo.Employment;
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


    /*
     특정 회사 조회 API
     */

    @GetMapping("/{companyId}")
    public ResponseEntity<Map<String, Object>> getCompanyById(@PathVariable Long companyId){
        Company company = companyService.getCompanyById(companyId);

        Map<String, Object> result = new HashMap<>();

        // 조회하는 회사 Id를 키값으로, 값은 company 객체
        result.put(String.valueOf(company.getCompany_id()), company);

        // 회사가 등록한 채용 공고 리스트를 값으로 함.
        List<Employment> employmentList = employmentService.getReducedEmploymentListByCompanyId(companyId);
        result.put("employment", employmentList);

        // 회사 소개 이미지 리스트를 값으로 함.
        List<Image> companyImageList = companyService.getCompanyImageListByCompanyId(companyId);
        result.put("companyImage", companyImageList);

        // 회사 태그 리스트를 값으로 함.
        List<CompanyTag> companyTagList = companyService.getCompanyTagListByCompanyId(companyId);
        result.put("companyTag", companyTagList);

        // 회사 뉴스 리스트를 값으로 함.
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


    @GetMapping("/allInfo")
    public ResponseEntity<CompanyInfo> getCompanyJoinedTableInfo(){
        return companyService.getCompanyJoinedTableInfo();
    }
}
