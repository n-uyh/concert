package com.hhplus.concert.domain.user;

import com.hhplus.concert.support.BaseErrorCode;
import com.hhplus.concert.support.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserException extends BaseException {

    public UserException(UserError userError) {
        super(userError);
    }

    @Getter
    @AllArgsConstructor
    public enum UserError implements BaseErrorCode {

        USER_NOT_FOUND(404, "US001", "사용자정보를 찾을 수 없습니다."),
        ;

        private final int status;
        private final String code;
        private final String message;

    }
}
