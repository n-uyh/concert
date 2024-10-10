package com.hhplus.concert.api.reservation;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReservationPayReqeust(
    @Schema(description = "예약id")
    long reservationId
) {

}
