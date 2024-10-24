package com.hhplus.concert.domain.concert;

import java.time.LocalDate;

public class ConcertCommand {
    public record Available(
        LocalDate from,
        LocalDate end
    ) {

    }

    public record Seat(
        long concertId
    ) {

    }

}
