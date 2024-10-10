package com.hhplus.concert.api.concert;

public record SeatResponse(
    long id,
    int seatNo,
    long price,
    boolean occupied
) {

}
