package com.hhplus.concert.domain.payment;

public interface PaymentOutboxRepository {

    void save(PaymentOutboxEntity outbox);

    PaymentOutboxEntity findNotProceeded(String eventId, EventType eventType);

}
