package com.hhplus.concert.interfaces.api.waiting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.concert.domain.payment.PaymentEvent;
import com.hhplus.concert.domain.waiting.WaitingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WaitingEventListener {

    private final WaitingService waitingService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "pay-completed", groupId = "pay-completed-wait")
    public void listenPayCompletedEvent(ConsumerRecord<String,byte[]> message) {
        try {
            PaymentEvent.PayCompleted event = objectMapper.readValue(message.value(),
                PaymentEvent.PayCompleted.class);
            log.info("payCompletedEvent consume : {}", event.token());
            waitingService.expireToken(event.token());
        } catch (Exception e) {
            log.warn("payCompletedEvent consume fail");
        }
    }

}
