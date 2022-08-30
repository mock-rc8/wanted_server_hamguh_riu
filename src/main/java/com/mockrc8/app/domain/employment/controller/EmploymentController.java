package com.mockrc8.app.domain.employment.controller;

import com.mockrc8.app.domain.company.service.CompanyService;
import com.mockrc8.app.domain.company.dto.Company;
import com.mockrc8.app.domain.company.dto.CompanyTag;
import com.mockrc8.app.domain.company.dto.Image;
import com.mockrc8.app.domain.employment.service.EmploymentService;
import com.mockrc8.app.domain.employment.dto.Employment;
import com.mockrc8.app.domain.employment.dto.TechSkill;
import com.mockrc8.app.domain.employment.vo.EmploymentLikeInfoVo;
import com.mockrc8.app.domain.employment.vo.ReducedEmploymentVo;
import com.mockrc8.app.domain.user.service.UserService;
import com.mockrc8.app.domain.user.vo.User;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.UserNotFoundException;
import com.mockrc8.app.global.oAuth.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.mockrc8.app.global.util.InfinityScroll.*;

@RestController
@RequestMapping("/employment")
@AllArgsConstructor
public class EmploymentController {

    private EmploymentService employmentService;
    private CompanyService companyService;
    private UserService userService;



    // 유저의 채용 북마크 API
    @PostMapping("/bookmark/{employmentId}")
    public ResponseEntity<Object> updateUserBookmark(@CurrentUser String userEmail,
                                                       @PathVariable Long employmentId){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userService.findUserByEmail(userEmail);
        Long userId = user.getUser_id();

        if (user == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        userService.updateUserBookmark(userId, employmentId);

        // 유저가 북마크했는지 하지 않았는지의 정보
        BaseResponse<String> response;
        if(userService.checkUserBookmarked(userId, employmentId) == 1){
            response = new BaseResponse<>("bookmarked");
        }else{
            response = new BaseResponse<>("not bookmarked");
        }

        return ResponseEntity.ok(response);
    }


    // 유저의 채용 좋아요 API
    @PostMapping("/like/{employmentId}")
    public ResponseEntity<Object> updateUserLike(@CurrentUser String userEmail,
                                                 @PathVariable Long employmentId){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userService.findUserByEmail(userEmail);

        if (user == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        userService.updateUserLike(user.getUser_id(), employmentId);
//        userService.updateUserLike(8L, employmentId);

        // 좋아요 버튼을 누를 때마다 좋아요의 수, 좋아요를 누른 유저들의 프로필 이미지가 필요함
        EmploymentLikeInfoVo employmentLikeInfoVo = employmentService.getEmploymentLikeInfoVo(employmentId);

        BaseResponse<EmploymentLikeInfoVo> response = new BaseResponse<>(employmentLikeInfoVo);
        return ResponseEntity.ok(response);
    }


    // 채용 ID로 특정 채용 조회 API
    @GetMapping("/{employmentId}")
    public ResponseEntity<Object> getEmploymentById(@CurrentUser String userEmail,
                                                    @PathVariable Long employmentId){
        Employment employment = employmentService.getEmploymentById(employmentId);
        Long companyId = employment.getCompanyId();

        // employment
        Map<String, Object> result = new HashMap<>();
        result.put("employment", employment);

        //company
        Company company = companyService.getCompanyById(companyId);
        result.put("company", company);

        // employment_image
        List<Image> imageList = employmentService.getEmploymentImageListByEmploymentId(employmentId);
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

        while(it.hasNext()){
            CompanyTag companyTag = it.next();

            List<Employment> employmentListByCompanyTagName = employmentService.getEmploymentListByCompanyTagName(companyTag.getCompanyTagName(), employmentId);
            Iterator<Employment> it2 = employmentListByCompanyTagName.iterator();

            while(it2.hasNext()){
                Employment employmentByCompanyTagName = it2.next();
                if(!employmentList.contains(employmentByCompanyTagName)){
                    employmentList.add(employmentByCompanyTagName);
                }

                if(employmentList.size() == maxCount){
                    break;
                }
            }
        }
        result.put("associatedEmployment", employmentList);

        // employment_tech_skill
        List<TechSkill> employmentTechSkillList = employmentService.getEmploymentTechSkillListByEmploymentId(employmentId);
        result.put("techSkill", employmentTechSkillList);

        // 이 채용을 좋아요한 유저의 수, 그 유저들의 프로필 이미지 목록
        EmploymentLikeInfoVo employmentLikeInfoVo = employmentService.getEmploymentLikeInfoVo(employmentId);
        result.put("employmentLikeInfo", employmentLikeInfoVo);


        // 로그인 된 유저인 경우에만 팔로우나 북마크 여부를 확인할 수 있음
        if(userEmail != null){
            User user = userService.findUserByEmail(userEmail);
            Long userId = user.getUser_id();
            if(userService.checkUserBookmarked(userId, employmentId) == 1){
                result.put("bookmarked", true);
            }else{
                result.put("bookmarked", false);
            }

            if(userService.checkUserCompanyFollowed(userId, companyId) == 1){
                result.put("followed", true);
            }else{
                result.put("followed", false);
            }
        }

        BaseResponse<Map<String, Object>> response = new BaseResponse<>(result);
        return ResponseEntity.ok(response);
    }


    // 인센티브 테마 채용 목록 조회 API
    @GetMapping("/themes/incentive")
    public ResponseEntity<Object> getEmploymentListByCompensation(HttpServletRequest request){
        // 스크롤 횟수를 헤더에서 찾아내는 메서드
        // global/util/InfinityScroll 클래스를 static import 해서 사용
        Integer scrollCount = getScrollCount(request);

        String[] tagNames = {"성과급", "상여금", "인센티브"};
        return employmentService.getEmploymentListByTagNames(tagNames, scrollCount);
    }

    // 연봉 상위 10% 테마 채용 목록 조회 API
    @GetMapping("/themes/salarytop")
    public ResponseEntity<Object> getEmploymentListBySalaryTop(HttpServletRequest request){
        Integer scrollCount = getScrollCount(request);

        String[] tagNames = {"연봉상위1%", "연봉상위2~5%", "연봉상위6~10%"};
        return employmentService.getEmploymentListByTagNames(tagNames, scrollCount);
    }

    // 주 4일 근무 테마 채용 목록 조회 API
    @GetMapping("/themes/4dayswork")
    public ResponseEntity<Object> getEmploymentListBy4DaysWork(HttpServletRequest request){
        Integer scrollCount = getScrollCount(request);

        String[] tagNames = {"야근없음", "주35시간", "주4일근무"};
        return employmentService.getEmploymentListByTagNames(tagNames, scrollCount);
    }


    // 재택 근무 테마 채용 목록 조회 API
    @GetMapping("/themes/workathome")
    public ResponseEntity<Object> getEmploymentListByWorkAtHome(HttpServletRequest request){
        Integer scrollCount = getScrollCount(request);

        String[] tagNames = {"유연근무", "재택근무", "원격근무"};
        return employmentService.getEmploymentListByTagNames(tagNames, scrollCount);
    }

    // 마감 임박 테마 채용 목록 조회 API
    @GetMapping("/themes/closesoon")
    public ResponseEntity<Object> getEmploymentListByCloseSoon(HttpServletRequest request){
        Integer scrollCount = getScrollCount(request);

        return employmentService.getEmploymentListByCloseSoon(scrollCount);
    }


    // 신입 채용 테마 채용 목록 조회 API
    @GetMapping("/themes/newcomer")
    public ResponseEntity<Object> getEmploymentListByNewcomer(HttpServletRequest request){
        Integer scrollCount = getScrollCount(request);

        return employmentService.getEmploymentListByNewcomer(scrollCount);
    }


    // 채용 목록 검색 API
    @GetMapping()
    public ResponseEntity<Object> getEmploymentList(HttpServletRequest request,
                                                    @RequestParam(required = false) Long jobGroupId,
                                                    @RequestParam(required = false) Long detailedJobGroupId,
                                                    @RequestParam(defaultValue = "latest") String sort,
                                                    @RequestParam(required = false) Long[] techSkillId,
                                                    @RequestParam(required = false) Integer minYear,
                                                    @RequestParam(required = false) Integer maxYear,
                                                    @RequestParam(required = false) Long[] tagId){


        Integer scroll = Integer.parseInt(request.getHeader("scrollCount"));
        if(scroll == null){
            scroll = 0;
        }
        // getEmploymentList메서드에 역할은 하나만 부여하되 mybatis의 if문을 통해 중복되는 코드를 줄였습니다.
        // 이 리스트를 기준으로 나머지 조건에 따라 교집합을 구하기 때문에, 여기서 정렬하도록 합니다.
        List<ReducedEmploymentVo> employmentList = employmentService.getEmploymentList(jobGroupId, detailedJobGroupId, sort);

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

        employmentList = employmentList.subList(scroll * 10, scroll * 10 + 10);
        HttpHeaders headers = new HttpHeaders();
        headers.set("scrollCount", Integer.toString(scroll + 1));



        BaseResponse<List<ReducedEmploymentVo>> response = new BaseResponse<>(employmentList);
        return new ResponseEntity(response, headers, HttpStatus.OK);

    }
}
