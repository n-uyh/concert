package com.hhplus.concert.domain.point;

import com.hhplus.concert.support.BaseErrorCode;
import com.hhplus.concert.support.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class PointException extends BaseException {

    public PointException(PointError pointError) {
        super(pointError);
    }

    @Getter
    @AllArgsConstructor
    public enum PointError implements BaseErrorCode {

        USER_POINT_NOT_FOUND(404, "UP001", "사용자 포인트 정보를 찾을 수 없습니다."),
        ;

        private final int status;
        private final String code;
        private final String message;

    }
}
