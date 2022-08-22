package com.mockrc8.app.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailValidationResponseDto {
    private boolean isSuccess;
    private String code;
    private String redirectPage;
    private String email;
}
