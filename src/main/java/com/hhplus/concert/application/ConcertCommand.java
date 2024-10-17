package com.hhplus.concert.application;

import java.time.LocalDate;

public class ConcertCommand {
    public record Available(
        String token,
        LocalDate from,
        LocalDate end
    ) {

    }

}
