package com.mockrc8.app.global.error.exception.company;

import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.BusinessException;

public class CompanyNotExistException extends BusinessException{
    private ErrorCode errorCode;

    public CompanyNotExistException(String message, ErrorCode errorCode) {
        super(message,errorCode);
        this.errorCode = errorCode;
    }
    public CompanyNotExistException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}

