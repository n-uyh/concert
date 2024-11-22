package com.hhplus.concert.infra.kafka.payment;

import com.hhplus.concert.domain.payment.PaymentEvent;
import com.hhplus.concert.domain.payment.PaymentProducer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProducerImpl implements PaymentProducer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    @Override
    public void producePayCompletedEvent(PaymentEvent.PayCompleted event) {
        log.info("pay-completed-event : {}", event);
        kafkaTemplate.send("pay-completed",event);
    }

    @Override
    public void batchProducePayCompletedEvent(List<PaymentEvent.PayCompleted> events) {
        log.info("batch produce pay-completed-event : {}", events);
        events.forEach(e -> kafkaTemplate.send("pay-completed", e));
    }
}
