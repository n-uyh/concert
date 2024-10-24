package com.hhplus.concert.domain.reservation;

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

        public static ReservedInfo of(ReservationEntity entity) {
            return new ReservedInfo(entity.getId(), entity.getUserId(), entity.getConcertSeatId(), entity.getPrice(), entity.getStatus().name(), entity.getCreatedAt(), entity.getExpiredAt());
        }

    }
}
