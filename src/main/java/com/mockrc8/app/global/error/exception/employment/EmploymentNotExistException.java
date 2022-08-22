package com.mockrc8.app.global.error.exception.employment;

import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.BusinessException;

public class EmploymentNotExistException extends BusinessException {
    private ErrorCode errorCode;

    public EmploymentNotExistException(String message, ErrorCode errorCode) {
        super(message,errorCode);
        this.errorCode = errorCode;
    }
    public EmploymentNotExistException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}


