package com.hhplus.concert.api.reservation;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReservationRequest(
    @Schema(description = "좌석id")
    long seatId
) {

}
