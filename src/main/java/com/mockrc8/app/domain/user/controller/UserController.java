package com.mockrc8.app.domain.user.controller;

import com.mockrc8.app.domain.employment.vo.ReducedEmploymentVo;
import com.mockrc8.app.domain.user.dto.*;
import com.mockrc8.app.domain.user.service.UserService;
import com.mockrc8.app.domain.user.vo.UserInterestTagVo;
import com.mockrc8.app.domain.user.vo.UserProfileVo;
import com.mockrc8.app.global.config.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Object> getUserProfile(@PathVariable Long userId){

        Map<String, Object> result = new HashMap<>();

        // 유저 정보 가져오기
        UserProfileVo userProfileVo = userService.getUserProfile(userId);
        result.put("userProfile", userProfileVo);

        //유저의 관심 태그 가져오기, 최대 2개까지만 보여준다.
        List<UserInterestTagVo> userInterestTagVoList = userService.getUserInterestTagVoByUserId(userId, 2);
        result.put("userInterestTagList", userInterestTagVoList);

        // 유저가 북마크한 채용 목록 가져오기 (최대 4개)
        List<ReducedEmploymentVo> userEmploymentBookmarkVoList = userService.getUserEmploymentBookmarkVoList(userId, 4);
        result.put("userEmploymentBookmarkList", userEmploymentBookmarkVoList);

        // 유저가 좋아요한 채용 목록 가져오기 (최대 4개)
        List<ReducedEmploymentVo> userEmploymentLikeVoList = userService.getUserEmploymentLikeVoList(userId, 4);
        result.put("userEmploymentLikeList", userEmploymentLikeVoList);

        BaseResponse<Map<String, Object>> response = new BaseResponse<>(result);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/{userId}/bookmark")
    public ResponseEntity<Object> getUserEmploymentBookmarkVoList(@PathVariable Long userId){
        List<ReducedEmploymentVo> reducedEmploymentVoList = userService.getUserEmploymentBookmarkVoList(userId, null);

        BaseResponse<List<ReducedEmploymentVo>> response = new BaseResponse<>(reducedEmploymentVoList);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/{userId}/like")
    public ResponseEntity<Object> getUserEmploymentLikeVoList(@PathVariable Long userId){
        List<ReducedEmploymentVo> reducedEmploymentVoList = userService.getUserEmploymentLikeVoList(userId, null);

        BaseResponse<List<ReducedEmploymentVo>> response = new BaseResponse<>(reducedEmploymentVoList);
        return ResponseEntity.ok(response);
    }






}
