package com.hhplus.concert.domain.payment;

import java.util.List;

public interface PaymentOutboxRepository {

    void save(PaymentOutboxEntity outbox);

    PaymentOutboxEntity findNotProceeded(String eventId, EventType eventType);

    List<PaymentOutboxEntity> findRepublishTargets(EventType eventType);

    long emptyPublished();
}
