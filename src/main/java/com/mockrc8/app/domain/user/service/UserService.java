package com.mockrc8.app.domain.user.service;

import com.mockrc8.app.domain.user.dto.*;
import com.mockrc8.app.domain.user.mapper.UserMapper;
import com.mockrc8.app.domain.user.vo.User;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.EmailDuplicationException;
import com.mockrc8.app.global.error.exception.User.PasswordNotMatchException;
import com.mockrc8.app.global.error.exception.User.PhoneNumberDuplicationException;
import com.mockrc8.app.global.error.exception.User.UserNotFoundException;
import com.mockrc8.app.global.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public ResponseEntity<UserRegisterResponseDto> registerUser(UserRegisterRequestDto userRegisterRequestDto) {
        if (userMapper.checkEmail((userRegisterRequestDto.getUserEmail())) == 1) {
            throw new EmailDuplicationException(ErrorCode.EMAIL_DUPLICATION);
        }
        if(userRegisterRequestDto.getUserPassword() != userRegisterRequestDto.getUserPasswordConfirm()){
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
}
