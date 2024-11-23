package com.hhplus.concert.domain.payment.event;

import java.util.UUID;

public class PaymentEvent {

    public record PayCompleted(
        String eventId,
        String token
    ) {
        public static PayCompleted of(String token) {
            return new PayCompleted(UUID.randomUUID().toString(),token);
        }

    }

}
