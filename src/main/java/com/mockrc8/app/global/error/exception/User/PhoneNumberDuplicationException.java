package com.mockrc8.app.global.error.exception.User;

import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.BusinessException;

public class PhoneNumberDuplicationException extends BusinessException {
    private ErrorCode errorCode;

    public PhoneNumberDuplicationException(String message, ErrorCode errorCode) {
        super(message,errorCode);
        this.errorCode = errorCode;
    }
    public PhoneNumberDuplicationException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
