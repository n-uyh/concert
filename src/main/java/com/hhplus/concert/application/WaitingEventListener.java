package com.hhplus.concert.application;

import com.hhplus.concert.domain.payment.PaymentEvent;
import com.hhplus.concert.domain.waiting.WaitingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class WaitingEventListener {

    private final WaitingService waitingService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listenPayCompletedEvent(PaymentEvent.PayCompleted event) {
        log.info("payCompletedEvent start : {}", event.token());
        waitingService.expireToken(event.token());
    }

}
