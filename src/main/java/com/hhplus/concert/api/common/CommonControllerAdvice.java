package com.hhplus.concert.api.common;

import com.hhplus.concert.support.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
class CommonControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonErrorResponse> handleException(Exception e) {
        return CommonErrorResponse.error(e, "ER999", "에러가 발생했습니다.", 500);
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<CommonErrorResponse> handleException(BaseException e) {
        return CommonErrorResponse.error(e);
    }
}
