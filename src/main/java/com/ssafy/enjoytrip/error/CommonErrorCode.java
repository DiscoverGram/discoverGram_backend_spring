package com.ssafy.enjoytrip.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    BINDING_ERROR(HttpStatus.BAD_REQUEST),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "User Not Found Error"),
    NOT_FOUND_POST(HttpStatus.BAD_REQUEST, "Post Not Found Error"),
    NOT_MATCHED_WRITER(HttpStatus.BAD_REQUEST, "Not Matched Writer"),
    USER_EXIST(HttpStatus.BAD_REQUEST, "User Already Exist");

    private final HttpStatus httpStatus;
    private String message;
    }
