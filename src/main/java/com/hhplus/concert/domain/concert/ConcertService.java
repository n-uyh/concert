package com.hhplus.concert.domain.concert;

import com.hhplus.concert.application.ConcertInfo;
import com.hhplus.concert.application.ConcertInfo.Seats;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;

    public List<ConcertInfo.Common> findAllAvailableConcertBetweenFromAndTo(LocalDate from, LocalDate end) {
        List<ConcertEntity> concerts = concertRepository.findAllByConcertDateBetween(
            from, end);

        return concerts.stream().map(ConcertInfo.Common::from).toList();
    }

    public List<ConcertInfo.Seats> findAllSeatsByConcertId(long concertId) {
        List<ConcertSeatEntity> seats = concertRepository.findAllSeatsByConcertId(
            concertId);

        return seats.stream().map(Seats::from).toList();
    }
}
