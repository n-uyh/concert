package com.hhplus.concert.domain.concert;

import com.hhplus.concert.support.BaseErrorCode;
import com.hhplus.concert.support.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ConcertException extends BaseException {

    public ConcertException(ConcertError concertError) {
        super(concertError);
    }

    @Getter
    @AllArgsConstructor
    public enum ConcertError implements BaseErrorCode {

        SEAT_ALREADY_OCCUPIED(404, "CS001", "이미 선택된 좌석입니다."),
        ;

        private final int status;
        private final String code;
        private final String message;

    }
}
