package com.hhplus.concert.api.payment;

import com.hhplus.concert.domain.payment.PaymentInfo;
import com.hhplus.concert.domain.reservation.ReservationInfo;

public class PaymentResponse {

    public record Payed(
        ReservationInfo.ReservedInfo reservation,
        PaymentInfo.Common payment
    ) {

        public static Payed of(PaymentInfo.PayedInfo info) {
            return new Payed(info.reservation(), info.payment());
        }
    }

}
