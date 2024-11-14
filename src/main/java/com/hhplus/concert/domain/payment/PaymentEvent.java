package com.hhplus.concert.domain.payment;

public class PaymentEvent {

    public record PayCompleted(
        String token
    ) {

        public static PayCompleted of(String token) {
            return new PayCompleted(token);
        }

    }

}
