package com.hhplus.concert.domain.payment;

public interface PaymentProducer {

    void producePayCompletedEvent(PaymentEvent.PayCompleted event);

}
