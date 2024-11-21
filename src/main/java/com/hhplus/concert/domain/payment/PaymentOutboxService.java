package com.hhplus.concert.domain.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentOutboxService {

    private final PaymentOutboxRepository outboxRepository;

    @Transactional
    public void occured(PaymentEvent.PayCompleted event) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            PaymentOutboxEntity entity = PaymentOutboxEntity.initialize(event.eventId(), EventType.PAY_COMPLETED,
                eventJson);
            outboxRepository.save(entity);
        } catch (JsonProcessingException e) {
            log.info("outboxService json parse error", e);
        }
    }

    @Transactional
    public void proceeded(PaymentEvent.PayCompleted event) {
        PaymentOutboxEntity notProceeded = outboxRepository.findNotProceeded(event.eventId(),
            EventType.PAY_COMPLETED);

        if (notProceeded == null) {
            log.info("outbox not found - event_id: {}", event.eventId());
        }

        outboxRepository.save(PaymentOutboxEntity.succeed(notProceeded));
    }


}
