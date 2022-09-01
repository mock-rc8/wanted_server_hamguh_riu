package com.mockrc8.app.domain.user.controller;

import com.mockrc8.app.domain.employment.vo.ReducedEmploymentVo;
import com.mockrc8.app.domain.resume.service.ResumeService;
import com.mockrc8.app.domain.resume.vo.Resume;
import com.mockrc8.app.domain.user.dto.*;
import com.mockrc8.app.domain.user.service.UserService;
import com.mockrc8.app.domain.user.vo.*;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.UserNotFoundException;
import com.mockrc8.app.global.oAuth.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;

import static com.mockrc8.app.global.util.InfinityScroll.getScrollCount;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody @Valid final UserRegisterRequestDto userRegisterRequestDto){
        return userService.registerUser(userRegisterRequestDto);
    }

    @GetMapping("/email-validation")
    public ResponseEntity<EmailValidationResponseDto> validateEmail(@RequestParam String email){
        return userService.validateEmail(email);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        return userService.loginUser(userLoginRequestDto);
    }

    @GetMapping("/login/{provider}")
    public ResponseEntity<Object> loginByThirdParty(@RequestParam String code, @PathVariable String provider){
        return userService.loginByThirdParty(code,provider);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getUserProfile(@CurrentUser String userEmail, @PathVariable Long userId){

        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        // 유저가 존재하는지, 유저가 일치하는지 검증
        userService.checkUserMatch(userEmail, userId);

        Map<String, Object> result = new HashMap<>();

        //유저 정보 가져오기
        UserProfileVo userProfileVo = userService.getUserProfile(userId);
        result.put("userProfile", userProfileVo);

        //유저의 관심 태그 가져오기, 최대 2개까지만 보여준다.
        List<UserInterestTagVo> userInterestTagVoList = userService.getUserInterestTagVoByUserId(userId, 2);
        result.put("userInterestTagList", userInterestTagVoList);

        // 유저가 북마크한 채용 목록 가져오기 (최대 4개)
        List<ReducedEmploymentVo> userEmploymentBookmarkVoList = userService.getUserEmploymentBookmarkVoList(userId, 4, null);
        result.put("userEmploymentBookmarkList", userEmploymentBookmarkVoList);

        // 유저가 좋아요한 채용 목록 가져오기 (최대 4개)
        List<ReducedEmploymentVo> userEmploymentLikeVoList = userService.getUserEmploymentLikeVoList(userId, 4, null);
        result.put("userEmploymentLikeList", userEmploymentLikeVoList);

        BaseResponse<Map<String, Object>> response = new BaseResponse<>(result);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/{userId}/bookmark")
    public ResponseEntity<Object> getUserEmploymentBookmarkVoList(HttpServletRequest request,
                                                                  @CurrentUser String userEmail,
                                                                  @PathVariable Long userId){

        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        userService.checkUserMatch(userEmail, userId);

        Integer scrollCount = getScrollCount(request);

        List<ReducedEmploymentVo> reducedEmploymentVoList = userService.getUserEmploymentBookmarkVoList(userId, null, scrollCount);

        BaseResponse<List<ReducedEmploymentVo>> response = new BaseResponse<>(reducedEmploymentVoList);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/{userId}/like")
    public ResponseEntity<Object> getUserEmploymentLikeVoList(HttpServletRequest request,
                                                              @CurrentUser String userEmail,
                                                              @PathVariable Long userId){

        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        userService.checkUserMatch(userEmail, userId);

        Integer scrollCount = getScrollCount(request);

        List<ReducedEmploymentVo> reducedEmploymentVoList = userService.getUserEmploymentLikeVoList(userId, null, scrollCount);

        BaseResponse<List<ReducedEmploymentVo>> response = new BaseResponse<>(reducedEmploymentVoList);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/{userId}/interests")
    public ResponseEntity<Object> getUserInterestTag(@CurrentUser String userEmail,
                                                     @PathVariable Long userId){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        userService.checkUserMatch(userEmail, userId);


        List<UserInterestTagVo> userInterestTagVoList = userService.getUserInterestTagVoByUserId(userId, null);
        BaseResponse<List<UserInterestTagVo>> response = new BaseResponse(userInterestTagVoList);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/user/{userId}/interests")
    public ResponseEntity<Object> updateUserInterestTag(@CurrentUser String userEmail,
                                                      @PathVariable Long userId,
                                                      @RequestParam  Long[] tagIds){

        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        userService.checkUserMatch(userEmail, userId);

        userService.updateUserInterestTag(userId, tagIds);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/user/" + userId + "/interests"));

        BaseResponse<String> response = new BaseResponse<>("redirect");
        return new ResponseEntity(response, headers, HttpStatus.MOVED_PERMANENTLY);

    }


    @GetMapping("/profile/{userId}")
    public ResponseEntity<Object> getUserExcludedCompany(@CurrentUser String userEmail,
                                                         @PathVariable Long userId){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        userService.checkUserMatch(userEmail, userId);



        Map<String, Object> map = new HashMap<>();


        // 유저 기본 정보
        UserProfileVo userProfileVo = userService.getUserProfile(userId);
        map.put("user", userProfileVo);



        if(userService.getResumesByUserId(userId).size() != 0) {
            // 유저 이력서 정보
            List<Resume> resumeList = userService.getResumesByUserId(userId);
            map.put("resume", resumeList);

            // 전문 분야
            if (userService.checkUserJobGroupExist(userId) == 1) {
                UserJobGroupVo userJobGroup = userService.getUserJobGroup(userId);
                List<UserDetailedJobGroupVo> userDetailedJobGroupList = userService.getUserDetailedJobGroupList(userId, userJobGroup.getJob_group_id());
                map.put("jobGroup", userJobGroup);
                map.put("detailedJobGroup", userDetailedJobGroupList);
            }

            // 추천인 정보
            UserProfileVo referralUserProfileVo = userService.getUserReferralId(userEmail);
            map.put("referralUser", referralUserProfileVo);

            // 유저 제외 기업 정보
            List<UserExcludedCompanyVo> userExcludedCompanyDtoList = userService.getUserExcludedCompanyVoList(userId);
            map.put("userExcludedCompany", userExcludedCompanyDtoList);
        }

        BaseResponse<Map<String, Object>> response = new BaseResponse<>(map);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/{userId}/exclude-company")
    public ResponseEntity<Object> getExcludeCompany(@CurrentUser String userEmail,
                                                    @PathVariable Long userId){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        userService.checkUserMatch(userEmail, userId);

        List<UserExcludedCompanyVo> userExcludedCompanyVoList = userService.getUserExcludedCompanyVoList(userId);

        BaseResponse<List<UserExcludedCompanyVo>> response = new BaseResponse<>(userExcludedCompanyVoList);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/profile/{userId}/exclude-company")
    public ResponseEntity<Object> excludeCompany(@CurrentUser String userEmail,
                                                 @PathVariable Long userId,
                                                 @RequestParam Long[] companyIds){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        userService.checkUserMatch(userEmail, userId);

        System.out.println(userId);
        userService.updateUserExcludedCompany(userId, companyIds);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/profile/" + userId));

        BaseResponse<String> response = new BaseResponse<>("redirect");
        return new ResponseEntity<>(response, headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
