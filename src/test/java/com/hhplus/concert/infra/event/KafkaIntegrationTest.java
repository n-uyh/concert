package com.hhplus.concert.infra.event;

import com.hhplus.concert.DynamicTestContainer;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
public class KafkaIntegrationTest extends DynamicTestContainer {

    @Autowired
    KafkaTemplate<Object,Object> kafkaTemplate;

    @Autowired
    TestConsumer testConsumer;

    @DisplayName("카프카 기본 연동 테스트 : 하나의 토픽에 대한 메세지 발행 시 컨슈머가 해당 메시지를 수신한다.")
    @Test
    void kafkaProducesMessageAndConsumeMessage() {
        String topic = "test-topic";
        String message = "test-message";

        kafkaTemplate.send(topic, message);

        Awaitility
            .await()
            .pollInterval(500, TimeUnit.MILLISECONDS)
            .atMost(Duration.ofSeconds(10))
            .untilAsserted(() -> {
                List<String> messages = testConsumer.getMessages();

                assertEquals(1, messages.size());
                assertEquals(message, messages.get(0));
            });
    }


}
