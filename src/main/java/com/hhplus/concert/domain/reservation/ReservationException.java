package com.hhplus.concert.domain.reservation;

import com.hhplus.concert.support.BaseErrorCode;
import com.hhplus.concert.support.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ReservationException extends BaseException {
    public ReservationException(ReserveError reserveError) {
        super(reserveError);
    }

    @Getter
    @AllArgsConstructor
    public enum ReserveError implements BaseErrorCode {

        EXPIRE_TARGETS_NOT_FOUND(404, "RE001", "임시배정만료 대상 예약이 없습니다.")
        ;

        private final int status;
        private final String code;
        private final String message;
    }
}
