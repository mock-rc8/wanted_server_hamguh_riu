package com.mockrc8.app.domain.user.controller;

import com.mockrc8.app.domain.user.dto.*;
import com.mockrc8.app.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    /**
     * 이메일 로그인 API
     * [POST] /login
     * @return ResponseEntity<UserLoginResponseDto>
     */
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        return userService.loginUser(userLoginRequestDto);
    }

}
