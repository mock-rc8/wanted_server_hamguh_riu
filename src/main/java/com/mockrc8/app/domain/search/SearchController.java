package com.mockrc8.app.domain.search;

import com.mockrc8.app.domain.company.service.CompanyService;
import com.mockrc8.app.domain.company.vo.CompanyListSearchedByTagVo;
import com.mockrc8.app.domain.employment.service.EmploymentService;
import com.mockrc8.app.domain.employment.vo.ReducedEmploymentVo;
import com.mockrc8.app.global.config.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockrc8.app.global.util.InfinityScroll.getScrollCount;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {

    private EmploymentService employmentService;
    private CompanyService companyService;


    @GetMapping()
    public ResponseEntity<Object> search(@RequestParam String query){
        HttpHeaders headers = new HttpHeaders();
        if(query.startsWith("#")){
            headers.setLocation(URI.create("/company/tag/" + query.substring(1)));

            return new ResponseEntity<>(headers, HttpStatus.PERMANENT_REDIRECT);
        }

        Map<String, Object> map = new HashMap<>();

        Integer companySize = companyService.countCompanyListByCompanyName(query);
        List<CompanyListSearchedByTagVo> companyList = companyService.getCompanyListByCompanyName(query, 1);
        System.out.println(companyList.size());

        map.put("companySize", companySize);
        map.put("company", companyList);


        List<ReducedEmploymentVo> reducedEmploymentVoList = employmentService.getEmploymentListByTitle(query, "latest");
        map.put("employmentSize", reducedEmploymentVoList.size());


        // 페이징 처리, offset 방식
        if(reducedEmploymentVoList.size() >= 6) {
            reducedEmploymentVoList = reducedEmploymentVoList.subList(0, 6);
        }

        map.put("employment", reducedEmploymentVoList);


        BaseResponse<Map<String, Object>> response = new BaseResponse<>(map);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employment")
    public ResponseEntity<Object> searchEmploymentByName(HttpServletRequest request,
                                                   @RequestParam String query,
                                                   @RequestParam(required = false) Long nationId,
                                                   @RequestParam(defaultValue = "latest") String sort,
                                                   @RequestParam(required = false) Long[] techSkillId,
                                                   @RequestParam(required = false) Integer minYear,
                                                   @RequestParam(required = false) Integer maxYear,
                                                   @RequestParam(required = false) Long[] tagId){
        Integer scroll = getScrollCount(request);

        List<ReducedEmploymentVo> employmentList = employmentService.getEmploymentListByTitle(query, sort);

        // 국가
        if(nationId != null){
            List<ReducedEmploymentVo> employmentListByNationId = employmentService.getEmploymentListByNationId(nationId);
            employmentList.retainAll(employmentListByNationId);
        }

        // 경력
        if(maxYear != null && minYear != null) {
            List<ReducedEmploymentVo> employmentListByCareerYear = employmentService.getEmploymentListByCareerYear(minYear, maxYear);
            employmentList.retainAll(employmentListByCareerYear);
        }

        // 기술스택
        if(techSkillId != null) {
            List<ReducedEmploymentVo> employmentListByTechSkill = employmentService.getEmploymentListByTechSkill(techSkillId);
            employmentList.retainAll(employmentListByTechSkill);
        }

        // 태그 ( 50명이하, 50명이상, 재택근무, 원격근무 등)
        if(tagId != null){
            List<ReducedEmploymentVo> employmentListByTechSkill = employmentService.getEmploymentListByTagId(tagId);
            employmentList.retainAll(employmentListByTechSkill);
        }

        // 페이징 처리, offset 방식
        if(employmentList.size() >= scroll * 2 + 2) {
            employmentList = employmentList.subList(scroll * 2, scroll * 2 + 2);
        }else{
            employmentList = employmentList.subList(scroll * 2, employmentList.size());
        }


        HttpHeaders headers = new HttpHeaders();
        headers.set("scrollCount", Integer.toString(scroll + 1));

        BaseResponse<List<ReducedEmploymentVo>> response = new BaseResponse<>(employmentList);
        return new ResponseEntity(response, headers, HttpStatus.OK);
    }


    @GetMapping("/company")
    public ResponseEntity<Object> searchCompanyByName(HttpServletRequest request,
                                                      @RequestParam String query){
        Integer scroll = getScrollCount(request);
        List<CompanyListSearchedByTagVo> companyList = companyService.getCompanyListByCompanyName(query, scroll);
        System.out.println(companyList.size());

        HttpHeaders headers = new HttpHeaders();
        headers.set("scrollCount", Integer.toString(scroll + 1));

        BaseResponse<List<CompanyListSearchedByTagVo>> response = new BaseResponse<>(companyList);
        return new ResponseEntity(response, headers, HttpStatus.OK);
    }

}
