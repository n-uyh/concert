package com.hhplus.concert.interfaces.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.concert.domain.payment.event.PaymentEvent;
import com.hhplus.concert.domain.payment.outbox.PaymentOutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentOutboxService outboxService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "pay-completed", groupId = "pay-completed-outbox")
    public void consumePayCompletedEvent(ConsumerRecord<String,byte[]> message) {
        try {
            PaymentEvent.PayCompleted event = objectMapper.readValue(message.value(), PaymentEvent.PayCompleted.class);

            log.info("handle payCompletedEvent - consume outbox :  {}", event.eventId());
            outboxService.proceeded(event);
        } catch (Exception e) {
            log.warn("outbox proceed fail");
        }
    }

}
