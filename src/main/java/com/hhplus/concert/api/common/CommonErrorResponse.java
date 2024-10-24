package com.hhplus.concert.api.common;

import com.hhplus.concert.support.BaseErrorCode;
import com.hhplus.concert.support.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public record CommonErrorResponse(
    String code,
    String message
) {

    public static ResponseEntity<CommonErrorResponse> error(Exception e, String code, String message, int status) {
        CommonErrorResponse error = new CommonErrorResponse(code, message);

        StackTraceElement trace = e.getStackTrace()[0];
        final Logger logger = LoggerFactory.getLogger(trace.getClassName());
        logger.error("[{}][{}][{}] - {}", trace.getLineNumber(),trace.getMethodName(),error.code(),error.message()); // error

        return error.response(status);
    }

    public static ResponseEntity<CommonErrorResponse> error(BaseException e) {
        BaseErrorCode code = e.getErrorCode();
        CommonErrorResponse error = new CommonErrorResponse(code.getCode(), code.getMessage());

        StackTraceElement trace = e.getStackTrace()[0];
        final Logger logger = LoggerFactory.getLogger(trace.getClassName());
        logger.info("[{}][{}][{}] - {}", trace.getLineNumber(),trace.getMethodName(),error.code(),error.message()); // info

        return error.response(code.getStatus());
    }

    public ResponseEntity<CommonErrorResponse> response(int status) {
        return ResponseEntity.status(status).body(this);
    }

}
