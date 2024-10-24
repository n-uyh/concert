package com.hhplus.concert.domain.concert;

import java.time.LocalDate;
import java.time.LocalTime;

public class ConcertInfo {

    public record Common(
        long id,
        String title,
        LocalDate concertDate,
        LocalTime startTime
    ) {

        public static Common of(ConcertEntity entity) {
            return new ConcertInfo.Common(entity.getId(), entity.getTitle(), entity.getConcertDate(), entity.getStartTime());
        }
    }

    public record SeatInfo(
        long seatId,
        int seatNo,
        long price,
        boolean occupied
    ) {

        public static SeatInfo of(ConcertSeatEntity entity) {
            return new SeatInfo(entity.getId(), entity.getSeatNo(), entity.getPrice(), entity.isOccupied());
        }
    }
}
