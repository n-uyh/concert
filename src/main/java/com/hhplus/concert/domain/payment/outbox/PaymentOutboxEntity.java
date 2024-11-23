package com.hhplus.concert.domain.payment.outbox;

import com.hhplus.concert.domain.payment.event.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "payment_outbox")
public class PaymentOutboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String eventId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(columnDefinition = "JSON")
    private String payload;

    private boolean status; // true: success, false: init

    public static PaymentOutboxEntity initialize(String eventId, EventType eventType, String payload) {
        return new PaymentOutboxEntity(0, eventId, eventType, payload, false);
    }

    public static PaymentOutboxEntity succeed(PaymentOutboxEntity target) {
        return new PaymentOutboxEntity(target.getId(), target.getEventId(), target.getEventType(), target.getPayload(), true);
    }

}
