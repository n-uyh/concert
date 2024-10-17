package com.hhplus.concert.domain.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "reservation")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long concertSeatId;
    private long userId;
    private long price;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public static final long EXPIRES_AFTER_SECONDS = 5 * 60;

    public static ReservationEntity create(long concertSeatId, long userId, long price, LocalDateTime createdAt) {
        return new ReservationEntity(0, concertSeatId,userId,price,ReservationStatus.RESERVED,createdAt,createdAt.plusSeconds(EXPIRES_AFTER_SECONDS));
    }

    public void updateExpired(LocalDateTime baseTime) {
        if (expiresAt.isBefore(baseTime)) {
            this.status = ReservationStatus.CANCELED;
        }
    }


}
