package com.mockrc8.app.domain.user.controller;

import com.mockrc8.app.domain.user.dto.UserRegisterRequestDto;
import com.mockrc8.app.domain.user.dto.UserRegisterResponseDto;
import com.mockrc8.app.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody @Valid final UserRegisterRequestDto userRegisterRequestDto){
        return userService.registerUser(userRegisterRequestDto);
    }

}
