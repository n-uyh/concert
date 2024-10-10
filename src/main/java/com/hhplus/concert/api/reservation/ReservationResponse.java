package com.hhplus.concert.api.reservation;

import java.time.LocalDateTime;

public record ReservationResponse(
    long id,
    long seatId,
    long price,
    String status,
    LocalDateTime reservedAt
) {

}
