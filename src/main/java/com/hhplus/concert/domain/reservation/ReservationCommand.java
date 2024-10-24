package com.hhplus.concert.domain.reservation;

public class ReservationCommand {
    public record ReserveSeat(
        long seatId,
        long userId
    ) {

    }

}
