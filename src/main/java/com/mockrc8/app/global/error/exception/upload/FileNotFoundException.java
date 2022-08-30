package com.mockrc8.app.global.error.exception.upload;

import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.BusinessException;

public class FileNotFoundException extends BusinessException {
    private ErrorCode errorCode;

    public FileNotFoundException(String message, ErrorCode errorCode) {
        super(message,errorCode);
        this.errorCode = errorCode;
    }
    public FileNotFoundException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
