package com.hhplus.concert.infra.db.payment;

import com.hhplus.concert.domain.payment.EventType;
import com.hhplus.concert.domain.payment.PaymentOutboxEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOutboxJpaRepository extends JpaRepository<PaymentOutboxEntity, Long> {

    PaymentOutboxEntity findByStatusFalseAndEventIdAndEventType(String eventId, EventType type);

    List<PaymentOutboxEntity> findAllByStatusFalseAndEventType(EventType eventType);

    long deleteAllByStatusTrue();

}
