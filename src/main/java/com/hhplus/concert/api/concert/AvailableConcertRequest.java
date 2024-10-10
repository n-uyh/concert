package com.hhplus.concert.api.concert;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record AvailableConcertRequest(
    @Schema(description = "검색날짜범위1")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate from,
    @Schema(description = "검색날짜범위2")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate end
) {
}
