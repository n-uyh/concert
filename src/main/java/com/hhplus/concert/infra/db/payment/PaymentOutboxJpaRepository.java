package com.hhplus.concert.infra.db.payment;

import com.hhplus.concert.domain.payment.EventType;
import com.hhplus.concert.domain.payment.PaymentOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOutboxJpaRepository extends JpaRepository<PaymentOutboxEntity, Long> {

    PaymentOutboxEntity findByEventIdAndEventType(String eventId, EventType type);

}
