package com.hhplus.concert.domain.payment;

public interface PaymentEventPublisher {

    void publishPayCompletedEvent(PaymentEvent.PayCompleted event);

}
