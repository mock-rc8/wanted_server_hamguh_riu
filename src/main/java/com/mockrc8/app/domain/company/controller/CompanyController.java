package com.mockrc8.app.domain.company.controller;


import com.mockrc8.app.domain.company.service.CompanyService;
import com.mockrc8.app.domain.company.dto.Company;
import com.mockrc8.app.domain.company.dto.CompanyNews;
import com.mockrc8.app.domain.company.dto.CompanyTag;
import com.mockrc8.app.domain.company.dto.Image;
import com.mockrc8.app.domain.company.vo.CompanyDetailVo;
import com.mockrc8.app.domain.company.vo.CompanyListSearchedByTagVo;
import com.mockrc8.app.domain.company.vo.CompanyTagGroupedTopicVo;
import com.mockrc8.app.domain.employment.service.EmploymentService;
import com.mockrc8.app.domain.employment.dto.Employment;
import com.mockrc8.app.domain.user.service.UserService;
import com.mockrc8.app.domain.user.vo.User;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.UserNotFoundException;
import com.mockrc8.app.global.oAuth.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockrc8.app.global.util.InfinityScroll.getScrollCount;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private CompanyService companyService;
    private UserService userService;


    // 유저의 회사 팔로우 API
    @PostMapping("/follow/{companyId}")
    public ResponseEntity<Object> updateUserCompanyFollow(@CurrentUser String userEmail,
                                                          @PathVariable Long companyId){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userService.findUserByEmail(userEmail);
        Long userId = user.getUser_id();

        if (user == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        userService.updateUserCompanyFollow(userId, companyId);

        // 유저가 팔로우 했는지 하지 않았는지의 정보
        BaseResponse<String> response;
        if(userService.checkUserCompanyFollowed(userId, companyId) == 1){
            response = new BaseResponse<>("followed");
        }else{
            response = new BaseResponse<>("not followed");
        }

        return ResponseEntity.ok(response);
    }

    /*
    회사 태그로 회사 목록 조회 API
    무한 스크롤 적용
     */
    @GetMapping("/tag/{hashtag_id}")
    public ResponseEntity<Object> getCompanyTagListByIdAndRandomList(HttpServletRequest request, @PathVariable Long hashtag_id){

        companyService.checkCompanyTagId(hashtag_id);

        Integer scrollCount = getScrollCount(request);

        Map<String, Object> result = new HashMap<>();
        List<CompanyTag> companyTagList = companyService.getCompanyTagListByIdAndRandomList(hashtag_id);
        result.put("searchedTagList", companyTagList);

        List<CompanyListSearchedByTagVo> companyList = companyService.getCompanyListByTagId(hashtag_id, scrollCount);
        result.put("companyList", companyList);

        List<CompanyTagGroupedTopicVo> companyTagListGroupedTopic = companyService.getCompanyTagGroupedByTopic();
        result.put("companyTagListGroupedTopic", companyTagListGroupedTopic);


        BaseResponse<Map<String, Object>> response = new BaseResponse<>(result);
        return ResponseEntity.ok(response);

    }

//    @GetMapping("/tag/{hashtag_id}")
//    public ResponseEntity<List<CompanyTag>> getCompanyTagListByIdAndRandomList(@PathVariable Long hashtag_id){
//        List<CompanyTag> companyTagList = companyService.getCompanyTagListByIdAndRandomList(hashtag_id);
//        return ResponseEntity.ok(companyTagList);
//    }

//    @GetMapping("tag_search/{")
//    public ResponseEntity<List<CompanyListSearchedByTagVo>> getCompanyListByTagId(){
//        List<CompanyListSearchedByTagVo> companyList = companyService.getCompanyListByTagId(5L);
//        return ResponseEntity.ok(companyList);
//
//    }


    /*
     특정 회사 조회 API
     */

    @GetMapping("/{companyId}")
    public ResponseEntity<Object> getCompanyJoinedTableInfo(@PathVariable Long companyId){
        return companyService.getCompanyJoinedTableInfo(companyId);
    }

//    @GetMapping("/{companyId}")
//    public ResponseEntity<Object> getCompanyById(@PathVariable Long companyId){
//        Company company = companyService.getCompanyById(companyId);
//
//        Map<String, Object> result = new HashMap<>();
//
//        // 조회하는 회사 Id를 키값으로, 값은 company 객체
//        result.put("company", company);
//
//        // 회사가 등록한 채용 공고 리스트를 값으로 함.
//        List<Employment> employmentList = employmentService.getReducedEmploymentListByCompanyId(companyId);
//        result.put("employment", employmentList);
//
//        // 회사 소개 이미지 리스트를 값으로 함.
//        List<Image> companyImageList = companyService.getCompanyImageListByCompanyId(companyId);
//        result.put("companyImage", companyImageList);
//
//        // 회사 태그 리스트를 값으로 함.
//        List<CompanyTag> companyTagList = companyService.getCompanyTagListByCompanyId(companyId);
//        result.put("companyTag", companyTagList);
//
//        // 회사 뉴스 리스트를 값으로 함.
//        List<CompanyNews> companyNewsList = companyService.getCompanyNewsListByCompanyId(companyId);
//        result.put("companyNews", companyNewsList);
//
//        BaseResponse<Map<String, Object>> response = new BaseResponse<>(result);
//        return ResponseEntity.ok(response);
//    }


    @PostMapping()
    public ResponseEntity<Company> registerCompany(@RequestBody Company company){
        Long companyId = companyService.registerCompany(company);

        Company companyById = companyService.getCompanyById(companyId);

        return ResponseEntity.ok(companyById);
    }

}
