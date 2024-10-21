package com.hhplus.concert.api.payment;

import com.hhplus.concert.domain.payment.PaymentInfo;
import com.hhplus.concert.domain.payment.PaymentInfo.CommonPayInfo;
import com.hhplus.concert.domain.reservation.ReservationInfo.ReservedInfo;

public class PaymentResponse {

    public record Payed(
        ReservedInfo reservation,
        CommonPayInfo payment
    ) {

        public static Payed of(PaymentInfo.PayedInfo info) {
            return new Payed(info.reservation(), info.payment());
        }
    }

}
