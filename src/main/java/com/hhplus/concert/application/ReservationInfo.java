package com.hhplus.concert.application;

import com.hhplus.concert.domain.reservation.ReservationEntity;
import java.time.LocalDateTime;

public class ReservationInfo {
    public record ReservedInfo(
        long id,
        long seatId,
        long price,
        String status,
        LocalDateTime reservedAt,
        LocalDateTime expiresAt
    ) {

        public static ReservedInfo from(ReservationEntity entity) {
            return new ReservedInfo(entity.getId(), entity.getConcertSeatId(), entity.getPrice(), entity.getStatus().name(), entity.getCreatedAt(), entity.getExpiresAt());
        }

    }
}
