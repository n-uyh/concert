package com.hhplus.concert.application;

public class PaymentCommand {

    public record CreatePayment(
        String token,
        long reservationId
    ) {

    }

}
