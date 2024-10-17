package com.hhplus.concert.infra.payment;

import com.hhplus.concert.domain.payment.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity,Long> {

}
