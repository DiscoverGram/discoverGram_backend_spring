package com.ssafy.enjoytrip.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    BINDING_ERROR(HttpStatus.BAD_REQUEST),
    NOT_FOUND_LIKE(HttpStatus.BAD_REQUEST, "LIKE Not Found Error"),
    NOT_MATCHED_WRITER(HttpStatus.BAD_REQUEST, "Not Matched Writer"),
    USER_EXIST(HttpStatus.BAD_REQUEST, "User Already Exist"),
    NOT_FOUND_MEMBER(HttpStatus.NO_CONTENT, "Member Not Found Error"),
    NOT_FOUND_COMMENT(HttpStatus.NO_CONTENT, "Comment Not Found Error"),
    NOT_FOUND_POST(HttpStatus.NO_CONTENT, "Post Not Found Error");


    private final HttpStatus httpStatus;
    private String message;
    }
