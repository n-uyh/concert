package com.hhplus.concert.domain.payment.event;

public interface PaymentEventPublisher {

    void publishPayCompletedEvent(PaymentEvent.PayCompleted event);

}
