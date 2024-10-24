package com.hhplus.concert.domain.concert;

import java.time.LocalDate;
import java.util.List;

public interface ConcertRepository {

    List<ConcertEntity> findAvailable(LocalDate from, LocalDate end);

    List<ConcertSeatEntity> findAllSeatsByConcertId(long concertId);

    ConcertSeatEntity findOneBySeatIdWithLock(long seatId);

    List<ConcertSeatEntity> findReleaseTargetSeats(List<Long> seatIds);
}
