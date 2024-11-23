package com.hhplus.concert.application;

import com.hhplus.concert.domain.payment.PaymentCommand;
import com.hhplus.concert.domain.payment.event.PaymentEvent;
import com.hhplus.concert.domain.payment.event.PaymentEventPublisher;
import com.hhplus.concert.domain.payment.PaymentInfo;
import com.hhplus.concert.domain.payment.PaymentService;
import com.hhplus.concert.domain.point.PointCommand;
import com.hhplus.concert.domain.point.PointService;
import com.hhplus.concert.domain.reservation.ReservationInfo;
import com.hhplus.concert.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final PointService pointService;
    private final PaymentEventPublisher paymentEventPublisher;

    @Transactional
    public PaymentInfo.PayedInfo createPayment(PaymentCommand.CreatePayment command) {
        ReservationInfo.ReservedInfo reserved = reservationService.findReservationWithStatusUpdate(
            command.reservationId());

        PaymentInfo.Common payment = paymentService.createPayment(reserved);

        pointService.pay(new PointCommand.Pay(payment.userId(), payment.price()));

        paymentEventPublisher.publishPayCompletedEvent(PaymentEvent.PayCompleted.of(command.token()));
        return new PaymentInfo.PayedInfo(reserved, payment);
    }

}
