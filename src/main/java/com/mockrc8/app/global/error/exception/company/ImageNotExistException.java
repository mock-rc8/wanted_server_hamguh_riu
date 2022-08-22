package com.mockrc8.app.global.error.exception.company;

import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.BusinessException;

public class ImageNotExistException extends BusinessException {
    private ErrorCode errorCode;

    public ImageNotExistException(String message, ErrorCode errorCode) {
        super(message,errorCode);
        this.errorCode = errorCode;
    }
    public ImageNotExistException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
