package com.mockrc8.app.domain.user.dto;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String userEmail;
    private String userPassword;
}
