package com.mockrc8.app.domain.user.dto;

import com.mockrc8.app.domain.user.vo.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserRegisterRequestDto {

    @NotNull
    @Email
    private String userEmail;
    private String userName;
    private String userPhoneNumber;
    @NotNull
    private String userPassword;
    private String userPasswordConfirm;

    public User toEntity(){
        return User.builder()
                .email(userEmail)
                .name(userName)
                .phone_number(userPhoneNumber)
                .password(userPassword)
                .build();
    }

}