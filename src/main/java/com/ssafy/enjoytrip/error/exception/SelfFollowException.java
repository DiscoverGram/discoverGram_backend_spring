package com.ssafy.enjoytrip.error.exception;

import com.ssafy.enjoytrip.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SelfFollowException extends RuntimeException{
    final ErrorCode errorCode;
}
