package com.hhplus.concert.infra.payment;

import com.hhplus.concert.domain.payment.PaymentEntity;
import com.hhplus.concert.domain.payment.PaymentHistoryEntity;
import com.hhplus.concert.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;
    private final PaymentHistoryJpaRepository historyRepository;

    @Override
    public void insertOne(PaymentEntity entity) {
        jpaRepository.save(entity);
    }

    @Override
    public void insertHistory(PaymentHistoryEntity history) {
        historyRepository.save(history);
    }
}
