package com.hhplus.concert.application;

import com.hhplus.concert.domain.payment.PaymentCommand;
import com.hhplus.concert.domain.payment.PaymentInfo.CommonPayInfo;
import com.hhplus.concert.domain.payment.PaymentInfo.PayedInfo;
import com.hhplus.concert.domain.payment.PaymentService;
import com.hhplus.concert.domain.reservation.ReservationInfo.ReservedInfo;
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

    public PayedInfo createPayment(PaymentCommand.CreatePayment command) {
        ReservedInfo reserved = reservationService.findReservationWithStatusUpdate(
            command.reservationId());

        CommonPayInfo payment = paymentService.createPayment(reserved);

        waitingService.expireToken(command.token());
        return new PayedInfo(reserved, payment);
    }

}
