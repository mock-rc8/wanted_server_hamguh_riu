package com.mockrc8.app.domain.user.service;

import com.mockrc8.app.domain.user.dto.UserRegisterRequestDto;
import com.mockrc8.app.domain.user.dto.UserRegisterResponseDto;
import com.mockrc8.app.domain.user.mapper.UserMapper;
import com.mockrc8.app.domain.user.vo.User;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.EmailDuplicationException;
import com.mockrc8.app.global.error.exception.User.PasswordNotMatchException;
import com.mockrc8.app.global.error.exception.User.PhoneNumberDuplicationException;
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


}
