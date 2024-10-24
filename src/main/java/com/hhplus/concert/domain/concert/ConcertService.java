package com.hhplus.concert.domain.concert;

import com.hhplus.concert.domain.concert.ConcertException.ConcertError;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;

    public List<ConcertInfo.Common> findAvailable(ConcertCommand.Available command) {
        List<ConcertEntity> concerts = concertRepository.findAvailable(command.from(), command.end());
        return concerts.stream().map(ConcertInfo.Common::of).toList();
    }

    public List<ConcertInfo.SeatInfo> findAvailableConcertSeats(ConcertCommand.Seat command) {
        List<ConcertSeatEntity> seats = concertRepository.findAllSeatsByConcertId(command.concertId());

        return seats.stream().map(ConcertInfo.SeatInfo::of).toList();
    }

    @Transactional
    public ConcertInfo.SeatInfo occupySeat(long seatId) {
        ConcertSeatEntity seat = concertRepository.findOneBySeatIdWithLock(seatId);
        seat.checkOccupied();
        seat.occupy();
        return ConcertInfo.SeatInfo.of(seat);
    }

    @Transactional
    public void releaseSeat(List<Long> seatIds) {
        List<ConcertSeatEntity> targets = concertRepository.findReleaseTargetSeats(seatIds);

        if (targets.isEmpty()) {
            throw new ConcertException(ConcertError.RELEASE_TARGETS_NOT_FOUND);
        }

        targets.forEach(ConcertSeatEntity::releaseOccupancy);
    }
}
