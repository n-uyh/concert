package com.hhplus.concert.api.reservation;

import com.hhplus.concert.domain.reservation.ReservationInfo.ReservedInfo;
import java.time.LocalDateTime;

public class ReservationResponse {

    public record Reserved(
        long id,
        long seatId,
        long price,
        String status,
        LocalDateTime reservedAt
    ) {
        public static Reserved of(ReservedInfo info) {
            return new Reserved(info.id(), info.seatId(), info.price(), info.status(), info.reservedAt());
        }
    }

}
