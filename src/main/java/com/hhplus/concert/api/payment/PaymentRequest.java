package com.hhplus.concert.api.payment;

import com.hhplus.concert.application.PaymentCommand;
import io.swagger.v3.oas.annotations.media.Schema;

public class PaymentRequest {

    public record CreatePayment(
        @Schema(description = "예약id")
        long reservationId
    ) {

        public PaymentCommand.CreatePayment toCommand(String token) {
            return new PaymentCommand.CreatePayment(token, reservationId);
        }
    }

}
