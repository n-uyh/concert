package com.hhplus.concert;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class TestContainersConfig {

    private static final KafkaContainer kafkaContainer;
    private static final GenericContainer<?> redisContainer;

    static {
        kafkaContainer = new KafkaContainer(DockerImageName.parse("apache/kafka"));
        redisContainer = new GenericContainer<>("redis:latest").withExposedPorts(6379);
    }

    @PostConstruct
    public void startContainers() {
        redisContainer.start();
        kafkaContainer.start();
    }

    @PreDestroy
    public void endContainers() {
        redisContainer.stop();
        kafkaContainer.stop();
    }

    public static KafkaContainer getKafkaContainer() {
        return kafkaContainer;
    }

    public static GenericContainer<?> getRedisContainer() {
        return redisContainer;
    }

}
