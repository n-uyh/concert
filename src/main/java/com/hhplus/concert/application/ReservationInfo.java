package com.hhplus.concert.application;

import com.hhplus.concert.domain.reservation.ReservationEntity;
import com.hhplus.concert.domain.reservation.ReservationStatus;
import java.time.LocalDateTime;

public class ReservationInfo {
    public record ReservedInfo(
        long id,
        long userId,
        long seatId,
        long price,
        String status,
        LocalDateTime reservedAt,
        LocalDateTime expiresAt
    ) {

        public boolean canPay() {
            return status.equals(ReservationStatus.RESERVED.name());
        }

        public static ReservedInfo from(ReservationEntity entity) {
            return new ReservedInfo(entity.getId(), entity.getUserId(), entity.getConcertSeatId(), entity.getPrice(), entity.getStatus().name(), entity.getCreatedAt(), entity.getExpiresAt());
        }

    }
}
