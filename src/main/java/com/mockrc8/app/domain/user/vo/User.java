package com.mockrc8.app.domain.user.vo;


import com.mockrc8.app.domain.user.dto.UserRegisterRequestDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long userId;
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