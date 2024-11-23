package com.hhplus.concert.interfaces.event;

import com.hhplus.concert.domain.payment.event.PaymentEvent;
import com.hhplus.concert.domain.payment.outbox.PaymentOutboxService;
import com.hhplus.concert.domain.payment.event.PaymentProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final PaymentOutboxService outboxService;
    private final PaymentProducer paymentProducer;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void payCompletedEventOccurred(PaymentEvent.PayCompleted event) {
        log.info("handle payCompletedEvent - outbox : {}", event.eventId());
        outboxService.occurred(event);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void producePayCompletedEvent(PaymentEvent.PayCompleted event) {
        log.info("handle payCompletedEvent - produce kafka : {}", event.eventId());
        paymentProducer.producePayCompletedEvent(event);
    }

}
