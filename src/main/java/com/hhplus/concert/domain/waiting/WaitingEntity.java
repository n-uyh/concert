package com.hhplus.concert.domain.waiting;

import com.hhplus.concert.domain.waiting.WaitingException.WaitingError;
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

    public static final int ACTIVATE_PERSONNEL = 3;

    public String status() {
        return status.name();
    }

    public void checkExpired() {
        if (status == WaitingStatus.EXPIRED) {
            throw new WaitingException(WaitingError.EXPIRED_TOKEN);
        }
    }

    public boolean isActive() {
        return status == WaitingStatus.ACTIVE;
    }

    public void expire() {
        this.status = WaitingStatus.EXPIRED;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        if (status == WaitingStatus.WAIT) {
            this.status = WaitingStatus.ACTIVE;
            this.updatedAt = LocalDateTime.now();
        }
    }
}
