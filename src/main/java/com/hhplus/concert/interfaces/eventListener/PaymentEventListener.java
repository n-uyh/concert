package com.hhplus.concert.interfaces.eventListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.concert.domain.payment.PaymentEvent;
import com.hhplus.concert.domain.payment.PaymentOutboxService;
import com.hhplus.concert.domain.payment.PaymentProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
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
    private final ObjectMapper objectMapper;

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

    @KafkaListener(topics = "pay-completed", groupId = "pay-completed-outbox")
    public void consumePayCompletedEvent(ConsumerRecord<String,byte[]> message) {
        try {
            PaymentEvent.PayCompleted event = objectMapper.readValue(message.value(), PaymentEvent.PayCompleted.class);

            log.info("handle payCompletedEvent - consume outbox :  {}", event.eventId());
            outboxService.proceeded(event);
        } catch (Exception e) {
            log.warn("outbox procceed fail");
        }
    }

}
