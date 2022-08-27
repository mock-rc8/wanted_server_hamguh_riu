package com.mockrc8.app.global.error.exception.User;

import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.BusinessException;

public class UnableFollowException extends BusinessException {
    private ErrorCode errorCode;

    public UnableFollowException(String message, ErrorCode errorCode) {
        super(message,errorCode);
        this.errorCode = errorCode;
    }
    public UnableFollowException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
