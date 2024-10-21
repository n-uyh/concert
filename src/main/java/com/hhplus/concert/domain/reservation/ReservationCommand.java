package com.hhplus.concert.domain.reservation;

public class ReservationCommand {
    public record ReserveSeat(
        String token,
        long seatId,
        long userId
    ) {

    }

}
