package com.hhplus.concert.api.concert;

import java.time.LocalDate;
import java.time.LocalTime;

public record ConcertResponse(
    long id,
    String title,
    LocalDate concertDate,
    LocalTime startTime
) {

}
