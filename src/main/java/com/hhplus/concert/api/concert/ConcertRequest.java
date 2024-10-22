package com.hhplus.concert.api.concert;

import com.hhplus.concert.domain.concert.ConcertCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class ConcertRequest {
    public record Available(
        @Schema(description = "검색날짜범위1")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate from,
        @Schema(description = "검색날짜범위2")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate end
    ) {

        public ConcertCommand.Available toCommand() {
            return new ConcertCommand.Available(from, end);
        }
    }

    public record Seat(
        @Schema(description = "공연id")
        long concertId
    ) {
        public ConcertCommand.Seat toCommand() {
            return new ConcertCommand.Seat(concertId);
        }
    }

}
