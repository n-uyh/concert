package com.hhplus.concert.application;

import com.hhplus.concert.application.PaymentInfo.CommonPayInfo;
import com.hhplus.concert.application.PaymentInfo.PayedInfo;
import com.hhplus.concert.application.ReservationInfo.ReservedInfo;
import com.hhplus.concert.domain.payment.PaymentService;
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
        waitingService.checkTokenIsActive(command.token());

        ReservedInfo reserved = reservationService.findReservationWithStatusUpdate(
            command.reservationId());

        CommonPayInfo payment = paymentService.createPayment(reserved);

        waitingService.expireToken(command.token());
        return new PayedInfo(reserved, payment);
    }

}
