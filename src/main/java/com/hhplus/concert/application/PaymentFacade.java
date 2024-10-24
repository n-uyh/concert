package com.hhplus.concert.application;

import com.hhplus.concert.domain.payment.PaymentCommand;
import com.hhplus.concert.domain.payment.PaymentInfo;
import com.hhplus.concert.domain.payment.PaymentService;
import com.hhplus.concert.domain.point.PointCommand;
import com.hhplus.concert.domain.point.PointService;
import com.hhplus.concert.domain.reservation.ReservationInfo;
import com.hhplus.concert.domain.reservation.ReservationService;
import com.hhplus.concert.domain.waiting.WaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final WaitingService waitingService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final PointService pointService;

    public PaymentInfo.PayedInfo createPayment(PaymentCommand.CreatePayment command) {
        ReservationInfo.ReservedInfo reserved = reservationService.findReservationWithStatusUpdate(
            command.reservationId());

        PaymentInfo.Common payment = paymentService.createPayment(reserved);

        pointService.pay(new PointCommand.Pay(payment.userId(), payment.price()));

        waitingService.expireToken(command.token());
        return new PaymentInfo.PayedInfo(reserved, payment);
    }

}
