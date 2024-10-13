package com.hhplus.concert.domain.waiting;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "waiting")
public class WaitingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    @Enumerated(EnumType.STRING)
    private WaitingStatus status;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static WaitingEntity create(LocalDateTime createdAt) {
        return new WaitingEntity(0,UUID.randomUUID().toString(),WaitingStatus.WAIT,createdAt,createdAt);
    }

    public String status() {
        return status.name();
    }
}
