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

        TOKEN_NOT_FOUND(404, "TK001", "토큰정보를 찾을 수 없습니다."),
        EXPIRED_TOKEN(401, "TK002", "만료된 토큰입니다."),
        NOT_ACTIVE_TOKEN(401,"TK003", "사용할 수 없는 토큰입니다."),
        ACTIVATE_TARGET_NOT_FOUND(404, "TK004", "활성화 대상 토큰이 없습니다.")
        ;

        private final int status;
        private final String code;
        private final String message;

    }

}
