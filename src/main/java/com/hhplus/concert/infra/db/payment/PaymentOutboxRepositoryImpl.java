package com.hhplus.concert.infra.db.payment;

import com.hhplus.concert.domain.payment.event.EventType;
import com.hhplus.concert.domain.payment.outbox.PaymentOutboxEntity;
import com.hhplus.concert.domain.payment.outbox.PaymentOutboxRepository;
import java.util.List;
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
        return jpaRepository.findByStatusFalseAndEventIdAndEventType(eventId, eventType);
    }

    @Override
    public List<PaymentOutboxEntity> findRepublishTargets(EventType eventType) {
        return jpaRepository.findAllByStatusFalseAndEventType(eventType);
    }

    @Override
    public long emptyPublished() {
        return jpaRepository.deleteAllByStatusTrue();
    }
}
