package com.mockrc8.app.domain.user.service;

import com.mockrc8.app.domain.employment.mapper.EmploymentMapper;
import com.mockrc8.app.domain.employment.vo.ReducedEmploymentVo;
import com.mockrc8.app.domain.user.dto.*;
import com.mockrc8.app.domain.user.mapper.UserMapper;
import com.mockrc8.app.domain.user.vo.*;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.EmailDuplicationException;
import com.mockrc8.app.global.error.exception.User.PasswordNotMatchException;
import com.mockrc8.app.global.error.exception.User.PhoneNumberDuplicationException;
import com.mockrc8.app.global.error.exception.User.UserNotFoundException;
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

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final EmploymentMapper employmentMapper;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ProviderService providerService;

    @Transactional
    public ResponseEntity<UserRegisterResponseDto> registerUser(UserRegisterRequestDto userRegisterRequestDto) {
        if (userMapper.checkEmail((userRegisterRequestDto.getUserEmail())) == 1) {
            throw new EmailDuplicationException(ErrorCode.EMAIL_DUPLICATION);
        }
        if(!userRegisterRequestDto.getUserPassword().equals(userRegisterRequestDto.getUserPasswordConfirm())){
            throw new PasswordNotMatchException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        if(userMapper.checkPhoneNumber(userRegisterRequestDto.getUserPhoneNumber()) == 1){
            throw new PhoneNumberDuplicationException(ErrorCode.PHONE_NUMBER_DUPLICATION);
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
                    .code("이메일 확인에 성공했습니다. 로그인 페이지로 리다이렉트 해주세요")
                    .redirectPage("/login")
                    .email(email)
                    .build();
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }else{
            final EmailValidationResponseDto dto = EmailValidationResponseDto.builder()
                    .isSuccess(true)
                    .code("이메일 확인에 실패했습니다. 회원가입 페이지로 리다이렉트 해주세요")
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
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(userLoginRequestDto.getUserPassword(), user.getPassword())) {
            throw new PasswordNotMatchException(ErrorCode.PASSWORD_NOT_MATCH);
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
                    .code("해당 써드파티와 연결된 회원이 없습니다. 회원가입 페이지로 리다이렉트 해주세요")
                    .redirectPage("/sign-up")
                    .email(profile.getEmail())
                    .build();
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }
    }


    public UserProfileVo getUserProfile(Long userId){
        return userMapper.getUserProfile(userId);
    }

    // 유저가 북마크한 누른 채용 목록( 이미지 하나, 소개글은 제외하는 간략화한 채용 객체 목록)
    public List<ReducedEmploymentVo> getUserEmploymentBookmarkVoList(Long userId, Integer maxCount){

        List<UserEmploymentBookmarkVo> userEmploymentBookmarkList = userMapper.getUserEmploymentBookmarkVoList(userId, maxCount);
        Iterator<UserEmploymentBookmarkVo> it = userEmploymentBookmarkList.iterator();

        List<ReducedEmploymentVo> reducedEmploymentVoList = new ArrayList<>();

        while(it.hasNext()){
            UserEmploymentBookmarkVo userEmploymentBookmarkVo = it.next();
            Long employmentId = userEmploymentBookmarkVo.getEmployment_id();
            ReducedEmploymentVo reducedEmployment = employmentMapper.getReducedEmploymentByEmploymentId(employmentId);

            reducedEmploymentVoList.add(reducedEmployment);
        }

        return reducedEmploymentVoList;
    }


    // 유저가 좋아요를 누른 채용 목록( 이미지 하나, 소개글은 제외하는 간략화한 채용 객체 목록)
    public List<ReducedEmploymentVo> getUserEmploymentLikeVoList(Long userId, Integer maxCount){

        List<UserEmploymentLikeVo> userEmploymentLikeVoList = userMapper.getUserEmploymentLikeVoList(userId, maxCount);
        Iterator<UserEmploymentLikeVo> it = userEmploymentLikeVoList.iterator();

        List<ReducedEmploymentVo> reducedEmploymentVoList = new ArrayList<>();

        while(it.hasNext()){
            UserEmploymentLikeVo userEmploymentBookmarkVo = it.next();
            Long employmentId = userEmploymentBookmarkVo.getEmployment_id();
            ReducedEmploymentVo reducedEmployment = employmentMapper.getReducedEmploymentByEmploymentId(employmentId);

            reducedEmploymentVoList.add(reducedEmployment);
        }

        return reducedEmploymentVoList;
    }

    public List<UserInterestTagVo> getUserInterestTagVoByUserId(Long userId, Integer maxCount){
        return userMapper.getUserInterestTagVoByUserId(userId, maxCount);
    }



//    public ResponseEntity<Object> getUserProfile(Long userId){
//        UserProfileVo userProfileVo = userMapper.getUserProfile(userId);
//
//        BaseResponse<UserProfileVo> response = new BaseResponse<>(userProfileVo);
//        return ResponseEntity.ok(response);
//    }


}
