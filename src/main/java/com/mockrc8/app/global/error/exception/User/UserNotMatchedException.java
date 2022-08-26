package com.mockrc8.app.global.error.exception.User;

import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.BusinessException;

public class UserNotMatchedException extends BusinessException {
    private ErrorCode errorCode;

    public UserNotMatchedException(String message, ErrorCode errorCode) {
        super(message,errorCode);
        this.errorCode = errorCode;
    }
    public UserNotMatchedException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
