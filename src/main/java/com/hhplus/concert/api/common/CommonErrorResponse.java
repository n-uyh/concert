package com.hhplus.concert.api.common;

import com.hhplus.concert.support.BaseErrorCode;

public record CommonErrorResponse(
    String code,
    String message
) {

    public static CommonErrorResponse error(BaseErrorCode code) {
        return new CommonErrorResponse(code.getCode(), code.getMessage());
    }
}
