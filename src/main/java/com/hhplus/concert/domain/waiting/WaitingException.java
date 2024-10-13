package com.hhplus.concert.domain.waiting;

import com.hhplus.concert.support.BaseErrorCode;
import com.hhplus.concert.support.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class WaitingException extends BaseException {

    public WaitingException(WaitingError errorCode) {
        super(errorCode);
    }

    @Getter
    @AllArgsConstructor
    public enum WaitingError implements BaseErrorCode {

        USER_NOT_FOUND(403, "US001", "알 수 없는 사용자입니다.");

        private final int status;
        private final String code;
        private final String message;

    }

}
