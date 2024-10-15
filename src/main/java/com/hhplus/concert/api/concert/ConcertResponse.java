package com.hhplus.concert.api.concert;

import com.hhplus.concert.application.ConcertInfo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ConcertResponse {

    public record ConcertList(
        List<CommonConcert> results
    ) {
        public static ConcertList from(List<ConcertInfo.Common> concert) {
            return new ConcertList(concert.stream().map(CommonConcert::from).toList());
        }
    }

    public record CommonConcert(
        long id,
        String title,
        LocalDate concertDate,
        LocalTime startTime
    ) {
        public static CommonConcert from(ConcertInfo.Common info) {
            return new CommonConcert(info.id(), info.title(), info.concertDate(), info.startTime());
        }
    }

    public record SeatList(
        List<SeatResult> seats
    ) {
        public static SeatList from(List<ConcertInfo.Seats> infos) {
            return new SeatList(infos.stream().map(SeatResult::from).toList());
        }

    }

    public record SeatResult(
        int seatNo,
        long price,
        boolean occupied
    ) {

        public static SeatResult from(ConcertInfo.Seats info) {
            return new SeatResult(info.seatNo(), info.price(), info.occupied());
        }
    }
}
