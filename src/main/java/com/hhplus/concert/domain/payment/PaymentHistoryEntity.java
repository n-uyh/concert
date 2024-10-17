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
@Table(name = "payment_history")
public class PaymentHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long paymentId;
    private long price;

    private PaymentType type;

    private LocalDateTime createdAt;

    public static PaymentHistoryEntity createPayHistory(long paymentId, long price, LocalDateTime createdAt) {
        return new PaymentHistoryEntity(0, paymentId, price, PaymentType.PAY, createdAt);
    }
}
