package com.hhplus.concert.api.payment;

import com.hhplus.concert.application.PaymentInfo;
import com.hhplus.concert.application.PaymentInfo.CommonPayInfo;
import com.hhplus.concert.application.ReservationInfo.ReservedInfo;

public class PaymentResponse {

    public record Payed(
        ReservedInfo reservation,
        CommonPayInfo payment
    ) {

        public static Payed from(PaymentInfo.PayedInfo info) {
            return new Payed(info.reservation(), info.payment());
        }
    }

}
