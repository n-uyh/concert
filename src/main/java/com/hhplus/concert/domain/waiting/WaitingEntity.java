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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "waiting")
public class WaitingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private String token;

    @Enumerated(EnumType.STRING)
    private WaitingStatus status;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public WaitingEntity(long userId) {
        this.userId = userId;
        this.token = UUID.randomUUID().toString();
        this.status = WaitingStatus.WAITING;
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public String status() {
        return status.getOption();
    }
}
