package com.mockrc8.app.domain.user.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mockrc8.app.domain.employment.mapper.EmploymentMapper;
import com.mockrc8.app.domain.employment.vo.ReducedEmploymentVo;
import com.mockrc8.app.domain.resume.mapper.ResumeMapper;
import com.mockrc8.app.domain.resume.vo.Resume;
import com.mockrc8.app.domain.user.dto.*;
import com.mockrc8.app.domain.user.mapper.UserMapper;
import com.mockrc8.app.domain.user.vo.*;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.*;
import com.mockrc8.app.global.oAuth.dto.AccessToken;
import com.mockrc8.app.global.oAuth.dto.ProfileDto;
import com.mockrc8.app.global.oAuth.service.ProviderService;
import com.mockrc8.app.global.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mockrc8.app.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final ResumeMapper resumeMapper;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ProviderService providerService;

    @Transactional
    public ResponseEntity<UserRegisterResponseDto> registerUser(UserRegisterRequestDto userRegisterRequestDto) {
        if (userMapper.checkEmail((userRegisterRequestDto.getUserEmail())) == 1) {
            throw new EmailDuplicationException(EMAIL_DUPLICATION);
        }
        if(!userRegisterRequestDto.getUserPassword().equals(userRegisterRequestDto.getUserPasswordConfirm())){
            throw new PasswordNotMatchException(PASSWORD_NOT_MATCH);
        }
        if(userMapper.checkPhoneNumber(userRegisterRequestDto.getUserPhoneNumber()) == 1){
            throw new PhoneNumberDuplicationException(PHONE_NUMBER_DUPLICATION);
        }
        userRegisterRequestDto.setUserPassword(passwordEncoder.encode(userRegisterRequestDto.getUserPassword()));
        final User userVO = userRegisterRequestDto.toEntity();
        userMapper.registerUser(userVO);
        final String userEmail = userRegisterRequestDto.getUserEmail();
        String jwtAccessToken = jwtService.createJwt(userEmail);
        UserRegisterResponseDto response = new UserRegisterResponseDto(jwtAccessToken, userEmail);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<EmailValidationResponseDto> validateEmail(String email) {
        final int result = userMapper.checkEmail(email);
        if(result == 1){
            final EmailValidationResponseDto dto = EmailValidationResponseDto.builder()
                    .isSuccess(true)
                    .code("????????? ????????? ??????????????????. ????????? ???????????? ??????????????? ????????????")
                    .redirectPage("/login")
                    .email(email)
                    .build();
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }else{
            final EmailValidationResponseDto dto = EmailValidationResponseDto.builder()
                    .isSuccess(true)
                    .code("????????? ????????? ??????????????????. ???????????? ???????????? ??????????????? ????????????")
                    .redirectPage("/sign-up")
                    .email(email)
                    .build();
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }

    }

    @Transactional
    public ResponseEntity<UserLoginResponseDto> loginUser(UserLoginRequestDto userLoginRequestDto) {
        final User user = userMapper.findUserByEmail(userLoginRequestDto.getUserEmail());
        if (user == null) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(userLoginRequestDto.getUserPassword(), user.getPassword())) {
            throw new PasswordNotMatchException(PASSWORD_NOT_MATCH);
        }
        String refreshToken = jwtService.createRefreshToken();
        int result = userMapper.updateRefreshToken(user.getUser_id(), refreshToken);
        UserLoginResponseDto response = new UserLoginResponseDto(
                user.getUser_id(),
                user.getEmail(),
                jwtService.createJwt(user.getEmail()),
                refreshToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Object> loginByThirdParty(String code, String provider) {
        AccessToken accessToken = providerService.getAccessToken(code, provider);
        ProfileDto profile = providerService.getProfile(accessToken.getAccess_token(), provider);
        String refreshToken = jwtService.createRefreshToken();
        String sessionAccessToken = jwtService.createJwt(profile.getEmail());
        if (userMapper.checkEmail(profile.getEmail()) == 1){
            User user = userMapper.findUserByEmail(profile.getEmail());
            userMapper.updateRefreshToken(user.getUser_id(),refreshToken);
            UserLoginResponseDto response = new UserLoginResponseDto(user.getUser_id(), user.getEmail(), sessionAccessToken, refreshToken);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            final EmailValidationResponseDto dto = EmailValidationResponseDto.builder()
                    .isSuccess(true)
                    .code("?????? ??????????????? ????????? ????????? ????????????. ???????????? ???????????? ??????????????? ????????????")
                    .redirectPage("/sign-up")
                    .email(profile.getEmail())
                    .build();
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }
    }

    public User findUserByEmail(String userEmail){
        return userMapper.findUserByEmail(userEmail);
    }


    public void checkUserMatch(String userEmail, Long userId){
        User user = userMapper.findUserByEmail(userEmail);  // ?????? ???????????? ????????? ??????
        UserProfileVo userProfile = userMapper.getUserProfile(userId);    // ????????? ??????????????? ????????? ??????

        if (user == null || userProfile == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        if(!user.getUser_id().equals(userProfile.getUser_id())){

            // ???????????? ????????? ?????? ????????? ????????? id??? ???????????? ?????? ??????
            throw new UserNotMatchedException(USER_NOT_MATCH);
        }
    }

    public UserProfileVo getUserReferralId(String userEmail){
        User user = userMapper.findUserByEmail(userEmail);
        return userMapper.getUserProfile(user.getReferral_id());
    }

    public UserJobGroupVo getUserJobGroup(Long userId){
        return userMapper.getUserJobGroup(userId);
    }


    public List<UserDetailedJobGroupVo> getUserDetailedJobGroupList(Long userId, Long jobGroupId){
        return userMapper.getUserDetailedJobGroupList(userId, jobGroupId);
    }

    public Integer checkUserJobGroupExist(Long userId){
        return userMapper.checkUserJobGroupExist(userId);
    }

    // ????????? ?????? ????????? ????????? ????????? 1, ???????????? 0 ??????
    public Integer checkUserBookmarked(Long userId, Long employmentId){
        Integer isChecked = userMapper.checkUserBookmarked(userId, employmentId);

        if(isChecked == 1){
            return 1;
        }
        return 0;
    }

    // ????????? ?????? ????????? ?????????????????? 1, ???????????? 0 ??????
    public Integer checkUserCompanyFollowed(Long userId, Long companyId){
        Integer isChecked = userMapper.checkUserCompanyFollowed(userId, companyId);

        if(isChecked == 1){
            return 1;
        }
        return 0;
    }


    public UserProfileVo getUserProfile(Long userId){
        return userMapper.getUserProfile(userId);
    }

    // ????????? ???????????? ?????? ?????? ??????( ????????? ??????, ???????????? ???????????? ???????????? ?????? ?????? ??????)
    public List<ReducedEmploymentVo> getUserEmploymentBookmarkVoList(Long userId, Integer maxCount, Integer scrollCount){
        if(scrollCount != null){
            PageHelper.startPage(scrollCount, 10);
        }
        return userMapper.getUserEmploymentBookmarkVoList(userId, maxCount);
    }


    // ????????? ???????????? ?????? ?????? ??????( ????????? ??????, ???????????? ???????????? ???????????? ?????? ?????? ??????)
    public List<ReducedEmploymentVo> getUserEmploymentLikeVoList(Long userId, Integer maxCount, Integer scrollCount){
        if(scrollCount != null){
            PageHelper.startPage(scrollCount, 10);
        }
        return userMapper.getUserEmploymentLikeVoList(userId, maxCount);

    }

    public List<UserInterestTagVo> getUserInterestTagVoByUserId(Long userId, Integer maxCount){
        return userMapper.getUserInterestTagVoByUserId(userId, maxCount);
    }

    public void updateUserBookmark(Long userId, Long employmentId){

        UserEmploymentBookmarkDto userEmploymentBookmarkDto = new UserEmploymentBookmarkDto(userId, employmentId);
        // ????????? ??????????????????
        if(userMapper.checkUserBookmarked(userId, employmentId) == 1){
            // ????????? ??????
            userMapper.deleteUserBookmark(userEmploymentBookmarkDto);

            // ???????????? ???????????? ????????????
            if(userMapper.checkUserBookmarked(userId, employmentId) == 1){
                throw new UnableBookmarkException(UNABLE_TO_BOOKMARK);
            }

        } else {
            // ?????????
            userMapper.registerUserBookmark(userEmploymentBookmarkDto);

            // ???????????? ???????????? ????????????
            if(userEmploymentBookmarkDto.getUser_employment_bookmark_id() == null){
                throw new UnableBookmarkException(UNABLE_TO_BOOKMARK);
            }
        }
    }

    public void updateUserLike(Long userId, Long employmentId){
        UserEmploymentLikeDto userEmploymentLikeDto = new UserEmploymentLikeDto(userId, employmentId);
        // ????????? ??????????????????
        if(userMapper.checkUserLiked(userId, employmentId) == 1){
            // ????????? ??????
            userMapper.deleteUserLike(userEmploymentLikeDto);

            // ???????????? ???????????? ????????????
            if(userMapper.checkUserBookmarked(userId, employmentId) == 1){
                throw new UnableLikeException(UNABLE_TO_LIKE);
            }

        } else {
            // ?????????
            userMapper.registerUserLike(userEmploymentLikeDto);

            // ???????????? ???????????? ????????????
            if(userEmploymentLikeDto.getUser_employment_like_id() == null){
                throw new UnableLikeException(UNABLE_TO_LIKE);
            }
        }
    }


    public void updateUserCompanyFollow(Long userId, Long companyId){

        UserCompanyFollowDto userCompanyFollowDto = new UserCompanyFollowDto(userId, companyId);
        // ????????? ??????????????????
        if(userMapper.checkUserCompanyFollowed(userId, companyId) == 1){
            // ????????? ??????
            userMapper.deleteUserCompanyFollow(userCompanyFollowDto);

            // ???????????? ???????????? ????????????
            if(userMapper.checkUserCompanyFollowed(userId, companyId) == 1){
                throw new UnableFollowException(UNABLE_TO_FOLLOW);
            }

        } else {
            // ?????????
            userMapper.registerUserCompanyFollow(userCompanyFollowDto);

            // ???????????? ???????????? ????????????
            if(userCompanyFollowDto.getUser_company_follow_id() == null){
                throw new UnableFollowException(UNABLE_TO_FOLLOW);
            }
        }
    }

    public void updateUserInterestTag(Long userId, Long[] tagIds){
        for(Long tagId : tagIds){
            UserInterestTagDto userInterestTagDto = new UserInterestTagDto(userId, tagId);
            if(userMapper.checkUserInterested(userId, tagId) == 1){
                userMapper.deleteUserInterestTag(userInterestTagDto);
            }else{
                userMapper.registerUserInterestTag(userInterestTagDto);
            }
        }
    }

    public void updateUserExcludedCompany(Long userId, Long[] companyIds){
        for(Long companyId : companyIds){
            System.out.println(userId + ", " + companyId);
            UserExcludedCompanyDto userExcludedCompanyDto = new UserExcludedCompanyDto(userId, companyId);
            if(userMapper.checkCompanyExcluded(userId, companyId) == 1){
                userMapper.deleteUserExcludedCompany(userExcludedCompanyDto);
            }else{
                userMapper.registerUserExcludedCompany(userExcludedCompanyDto);
            }
        }
    }


    public List<UserExcludedCompanyVo> getUserExcludedCompanyVoList(Long userId){
        return userMapper.getUserExcludedCompanyVoList(userId);
    }


    public List<Resume> getResumesByUserId(Long userId){
        return resumeMapper.getResumesByUserId(userId);
    }


//    public ResponseEntity<Object> getUserProfile(Long userId){
//        UserProfileVo userProfileVo = userMapper.getUserProfile(userId);
//
//        BaseResponse<UserProfileVo> response = new BaseResponse<>(userProfileVo);
//        return ResponseEntity.ok(response);
//    }


}
