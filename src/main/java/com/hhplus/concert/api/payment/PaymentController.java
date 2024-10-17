package com.hhplus.concert.api.payment;

import com.hhplus.concert.api.payment.PaymentResponse.Payed;
import com.hhplus.concert.application.PaymentFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController implements IPaymentController {

    private final PaymentFacade paymentFacade;

    @PostMapping
    public ResponseEntity<PaymentResponse.Payed> payToReservation(
        @RequestHeader("Hh-Waiting-Token") String token,
        @RequestBody PaymentRequest.CreatePayment reqeust
    ) {
        Payed result = Payed.from(paymentFacade.createPayment(reqeust.toCommand(token)));
        return ResponseEntity.ok(result);
    }
}
