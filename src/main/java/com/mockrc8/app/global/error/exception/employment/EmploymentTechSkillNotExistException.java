package com.mockrc8.app.global.error.exception.employment;

import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.BusinessException;

public class EmploymentTechSkillNotExistException extends BusinessException {
    private ErrorCode errorCode;

    public EmploymentTechSkillNotExistException(String message, ErrorCode errorCode) {
        super(message,errorCode);
        this.errorCode = errorCode;
    }
    public EmploymentTechSkillNotExistException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
