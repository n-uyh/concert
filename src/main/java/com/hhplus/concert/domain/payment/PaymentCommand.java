package com.hhplus.concert.domain.payment;

public class PaymentCommand {

    public record CreatePayment(
        String token,
        long reservationId
    ) {

    }

}
