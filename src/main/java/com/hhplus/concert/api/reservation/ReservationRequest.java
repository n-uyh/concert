package com.hhplus.concert.api.reservation;

import com.hhplus.concert.domain.reservation.ReservationCommand;
import io.swagger.v3.oas.annotations.media.Schema;

public class ReservationRequest {

    public record Seat(
        @Schema(description = "좌석id")
        long seatId,
        @Schema(description = "사용자id")
        long userId
    ) {
        public ReservationCommand.ReserveSeat toCommand(String token) {
            return new ReservationCommand.ReserveSeat(token, seatId, userId);
        }
    }
}
