package com.hhplus.concert.api.common;

import com.hhplus.concert.support.BaseErrorCode;
import com.hhplus.concert.support.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class CommonControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(500).body(new CommonErrorResponse("500", "에러가 발생했습니다."));
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<CommonErrorResponse> handleException(BaseException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus()).body(CommonErrorResponse.error(errorCode));
    }

}
