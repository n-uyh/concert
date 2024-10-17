package com.hhplus.concert.domain.payment;

public interface PaymentRepository {

    void insertOne(PaymentEntity entity);

    void insertHistory(PaymentHistoryEntity history);

}
