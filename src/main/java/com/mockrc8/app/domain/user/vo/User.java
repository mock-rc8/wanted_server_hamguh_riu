package com.mockrc8.app.domain.user.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.mockrc8.app.domain.user.dto.UserRegisterRequestDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class User {
    private Long user_id;
    private String name;
    private String email;
    private String password;
    private String phone_number;
    private String profile_image;
    private Long referral_id;
    private int career_year;
    private int salary;
    private String provider;
    private String refresh_token;
}