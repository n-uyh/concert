package com.hhplus.concert.domain.payment;

import com.hhplus.concert.support.BaseErrorCode;
import com.hhplus.concert.support.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class PaymentException extends BaseException {

    public PaymentException(PayError payError) {
        super(payError);
    }

    @Getter
    @AllArgsConstructor
    public enum PayError implements BaseErrorCode {

        CANNOT_PAY(409,"PAY001","결제할 수 없는 예약건입니다.")
        ;

        private final int status;
        private final String code;
        private final String message;

    }
}
