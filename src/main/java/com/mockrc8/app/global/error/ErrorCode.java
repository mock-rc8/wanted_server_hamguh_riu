package com.mockrc8.app.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),

    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // User
    EMAIL_DUPLICATION(400, "U001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "U002", "Login input is invalid"),
    USER_NOT_FOUND(400,"U003", "User Not Found"),
    PASSWORD_NOT_MATCH(400,"U004", "Password Not Match"),
    PHONE_NUMBER_DUPLICATION(400, "U005","이미 존재하는 핸드폰 번호입니다"),
    USER_NOT_MATCH(400, "U006", "User Not match"),
    UNABLE_TO_BOOKMARK(400, "U007", "User cannot change bookmark status"),
    UNABLE_TO_LIKE(400, "U008", "User cannot change like status"),
    UNABLE_TO_FOLLOW(400, "U009", "User cannot change follow status"),
    USER_JOB_GROUP_NOT_FOUND(400, "U010", "User job group not found"),
    USER_DETAILED_JOB_GROUP_NOT_FOUND(400, "U011", "User detailed job group not found"),

    //Company
    COMPANY_NOT_EXIST(400, "COM001", "Company not exists"),
    COMPANY_TAG_NOT_EXIST(400, "COM002", "CompanyTag not exists"),


    //Employment
    EMPLOYMENT_NOT_EXIST(400, "E001", "Employment not exists"),
    EMPLOYMENT_TECH_SKILL_NOT_EXIST(400, "E002","EmploymentTechSkill not exists"),

    //Image
    IMAGE_NOT_EXIST(400, "I001", "Image not exists"),

    FILE_SIZE_EXCEED(400, "F001", "파일 용량 초과"),
    FILE_UPLOAD_FAIL(400, "F002", "파일 업로드 실패"),
    FILE_NOT_EXIST(400,"F003", "파일이 없습니다."),
    FILE_DOWNLOAD_FAIL(400, "F004", "파일 다운로드 실패");


    private final int status;
    private final String code;
    private final String message;
}
