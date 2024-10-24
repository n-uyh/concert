package com.hhplus.concert.api.concert;

import com.hhplus.concert.domain.concert.ConcertInfo;
import com.hhplus.concert.domain.concert.ConcertInfo.SeatInfo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ConcertResponse {

    public record ConcertList(
        List<CommonConcert> results
    ) {
        public static ConcertList of(List<ConcertInfo.Common> concert) {
            return new ConcertList(concert.stream().map(CommonConcert::of).toList());
        }
    }

    public record CommonConcert(
        long id,
        String title,
        LocalDate concertDate,
        LocalTime startTime
    ) {
        public static CommonConcert of(ConcertInfo.Common info) {
            return new CommonConcert(info.id(), info.title(), info.concertDate(), info.startTime());
        }
    }

    public record SeatList(
        List<SeatResult> seats
    ) {
        public static SeatList of(List<SeatInfo> infos) {
            return new SeatList(infos.stream().map(SeatResult::from).toList());
        }

    }

    public record SeatResult(
        int seatNo,
        long price,
        boolean occupied
    ) {

        public static SeatResult from(SeatInfo info) {
            return new SeatResult(info.seatNo(), info.price(), info.occupied());
        }
    }
}
