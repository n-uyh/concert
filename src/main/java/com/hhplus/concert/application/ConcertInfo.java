package com.hhplus.concert.application;

import com.hhplus.concert.domain.concert.ConcertEntity;
import com.hhplus.concert.domain.concert.ConcertSeatEntity;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConcertInfo {

    public record Common(
        long id,
        String title,
        LocalDate concertDate,
        LocalTime startTime
    ) {

        public static Common from(ConcertEntity entity) {
            return new ConcertInfo.Common(entity.getId(), entity.getTitle(), entity.getConcertDate(), entity.getStartTime());
        }
    }

    public record Seats(
        int seatNo,
        long price,
        boolean occupied
    ) {

        public static Seats from(ConcertSeatEntity entity) {
            return new ConcertInfo.Seats(entity.getSeatNo(), entity.getPrice(), entity.isOccupied());
        }
    }
}
