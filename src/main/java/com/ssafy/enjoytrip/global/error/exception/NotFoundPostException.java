package com.ssafy.enjoytrip.global.error.exception;

import com.ssafy.enjoytrip.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotFoundPostException extends RuntimeException{
    final ErrorCode errorCode;
}
