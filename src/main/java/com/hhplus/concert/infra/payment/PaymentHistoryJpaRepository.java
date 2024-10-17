package com.hhplus.concert.infra.payment;

import com.hhplus.concert.domain.payment.PaymentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryJpaRepository extends JpaRepository<PaymentHistoryEntity, Long> {

}
