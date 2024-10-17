package com.hhplus.concert.application;

public class ReservationCommand {
    public record ReserveSeat(
        String token,
        long seatId,
        long userId
    ) {

    }

}
