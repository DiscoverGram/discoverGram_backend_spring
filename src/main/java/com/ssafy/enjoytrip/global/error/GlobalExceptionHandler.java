package com.ssafy.enjoytrip.global.error;

import com.ssafy.enjoytrip.global.error.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindingException.class)
    public ResponseEntity<Object> bindingError(BindingException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionBinding(errorCode,e.getMessage());
    }
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Object> existUser(UserExistException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }
    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<Object> notFoundUser(NotFoundMemberException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(NotMatchedWriterException.class)
    public ResponseEntity<Object> notMatchedWriter(NotMatchedWriterException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(NotFoundPostException.class)
    public ResponseEntity<Object> notFoundArticle(NotFoundPostException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<Object> handleExceptionBinding(ErrorCode errorCode,String message) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorDTO.builder()
                        .code(errorCode.name())
                        .message(message)
                        .build());
    }
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorDTO.builder()
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build());
    }
}
