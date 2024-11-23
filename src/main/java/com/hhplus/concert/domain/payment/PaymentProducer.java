package com.hhplus.concert.domain.payment;

import java.util.List;

public interface PaymentProducer {

    void producePayCompletedEvent(PaymentEvent.PayCompleted event);

    void batchProducePayCompletedEvent(List<PaymentEvent.PayCompleted> events);

}
