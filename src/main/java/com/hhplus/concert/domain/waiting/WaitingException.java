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

        USER_NOT_FOUND(404, "US001", "알 수 없는 사용자입니다."),
        TOKEN_NOT_FOUND(404, "TK001", "토큰정보를 찾을 수 없습니다."),
        EXPIRED_TOKEN(401, "TK002", "만료된 토큰입니다.")
        ;

        private final int status;
        private final String code;
        private final String message;

    }

}
