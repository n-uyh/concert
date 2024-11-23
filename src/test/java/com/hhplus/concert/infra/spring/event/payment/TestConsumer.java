package com.hhplus.concert.infra.spring.event.payment;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestConsumer {

    @Getter
    private List<String> messages = new ArrayList<>();

    @KafkaListener(topics = "test-topic")
    public void consume(String msg, Acknowledgment ack) {
        try {
            messages.add(msg);
            log.info("Consumed message: {}", msg);

            ack.acknowledge();
        } catch (Exception e) {
            log.error("Failed to consume message", e);
        }
    }


}
