package com.hhplus.concert.infra.db.payment;

import com.hhplus.concert.domain.payment.EventType;
import com.hhplus.concert.domain.payment.PaymentOutboxEntity;
import com.hhplus.concert.domain.payment.PaymentOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentOutboxRepositoryImpl implements PaymentOutboxRepository {

    private final PaymentOutboxJpaRepository jpaRepository;

    @Override
    public void save(PaymentOutboxEntity entity) {
        jpaRepository.save(entity);
    }

    @Override
    public PaymentOutboxEntity findNotProceeded(String eventId, EventType eventType) {
        return jpaRepository.findByEventIdAndEventType(eventId, eventType);
    }
}
