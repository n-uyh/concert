package com.hhplus.concert.domain.concert;

import java.time.LocalDate;
import java.util.List;

public interface ConcertRepository {

    List<ConcertEntity> findAllByConcertDateBetween(LocalDate from, LocalDate end);

    List<ConcertSeatEntity> findAllSeatsByConcertId(long concertId);

}
