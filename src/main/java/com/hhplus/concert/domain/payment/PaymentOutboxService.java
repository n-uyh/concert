package com.hhplus.concert.domain.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentOutboxService {

    private final PaymentOutboxRepository outboxRepository;
    private final PaymentProducer paymentProducer;

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
        } else {
            outboxRepository.save(PaymentOutboxEntity.succeed(notProceeded));
        }
    }


    public void republishPayCompletedEvents() {
        List<PaymentOutboxEntity> targets = outboxRepository.findRepublishTargets(EventType.PAY_COMPLETED);
        log.info("pay-completed-event republish targets size: {}", targets.size());
        ObjectMapper objectMapper = new ObjectMapper();

        List<PaymentEvent.PayCompleted> events = targets.stream()
            .map(t -> {
                try {
                    return objectMapper.readValue(t.getPayload(),
                        PaymentEvent.PayCompleted.class);
                } catch (JsonProcessingException e) {
                    log.error("pay-completed-event republish jsonprocessing error");
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());

        paymentProducer.batchProducePayCompletedEvent(events);
    }


    @Transactional
    public void emptyPublished() {
        long deleted = outboxRepository.emptyPublished();
        log.info("payment outbox deleted size : {}", deleted);
    }


}
