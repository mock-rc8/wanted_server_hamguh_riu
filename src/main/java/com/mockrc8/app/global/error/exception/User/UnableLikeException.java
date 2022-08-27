package com.mockrc8.app.global.error.exception.User;

import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.BusinessException;

public class UnableLikeException extends BusinessException {
    private ErrorCode errorCode;

    public UnableLikeException(String message, ErrorCode errorCode) {
        super(message,errorCode);
        this.errorCode = errorCode;
    }
    public UnableLikeException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
