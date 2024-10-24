package com.hhplus.concert.domain.payment;

import com.hhplus.concert.domain.payment.PaymentException.PayError;
import com.hhplus.concert.domain.reservation.ReservationInfo.ReservedInfo;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentInfo.Common createPayment(ReservedInfo info) {
        if (!info.canPay()) {
            throw new PaymentException(PayError.CANNOT_PAY);
        }

        PaymentEntity payment = PaymentEntity.create(info.userId(), info.id(), info.price(),
            LocalDateTime.now());

        paymentRepository.insertOne(payment);

        PaymentHistoryEntity history = PaymentHistoryEntity.createPayHistory(payment.getId(),
            payment.getPrice(), payment.getCreatedAt());

        paymentRepository.insertHistory(history);

        return PaymentInfo.Common.of(payment);
    }

}
