package com.hhplus.concert.domain.concert;

import com.hhplus.concert.domain.concert.ConcertInfo.SeatInfo;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;

    public List<ConcertInfo.Common> findAllAvailableConcertBetweenFromAndTo(LocalDate from, LocalDate end) {
        List<ConcertEntity> concerts = concertRepository.findAllByConcertDateBetween(
            from, end);

        return concerts.stream().map(ConcertInfo.Common::of).toList();
    }

    public List<SeatInfo> findAllSeatsByConcertId(long concertId) {
        List<ConcertSeatEntity> seats = concertRepository.findAllSeatsByConcertId(
            concertId);

        return seats.stream().map(SeatInfo::of).toList();
    }

    @Transactional
    public SeatInfo findAndOccupySeat(long seatId) {
        ConcertSeatEntity seat = concertRepository.findOneBySeatIdWithLock(seatId);
        seat.checkOccupied();
        seat.occupy();
        return SeatInfo.of(seat);
    }
}
