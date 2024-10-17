package com.hhplus.concert.domain.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;
    private long reservationId;
    private long price;

    private PaymentStatus status;

    private LocalDateTime createdAt;

    public static PaymentEntity create(long userId, long reservationId, long price, LocalDateTime createdAt) {
        return new PaymentEntity(0, userId, reservationId, price, PaymentStatus.WAIT, createdAt);
    }

}
