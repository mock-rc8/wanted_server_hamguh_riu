package com.mockrc8.app.global.error.exception.upload;

import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.BusinessException;

public class FileUploadFailedException extends BusinessException {
    private ErrorCode errorCode;

    public FileUploadFailedException(String message, ErrorCode errorCode) {
        super(message,errorCode);
        this.errorCode = errorCode;
    }
    public FileUploadFailedException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
