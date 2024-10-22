package com.hhplus.concert.application;

import com.hhplus.concert.domain.concert.ConcertInfo;
import com.hhplus.concert.domain.concert.ConcertInfo.SeatInfo;
import com.hhplus.concert.domain.concert.ConcertCommand;
import com.hhplus.concert.domain.concert.ConcertService;
import com.hhplus.concert.domain.waiting.WaitingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertFacade {

    private final ConcertService concertService;

    public List<ConcertInfo.Common> findAllAvailable(ConcertCommand.Available command) {
        return concertService.findAllAvailableConcertBetweenFromAndTo(command.from(), command.end());
    }

    public List<SeatInfo> findAllSeatsByConcertId(String token, long concertId) {
        return concertService.findAllSeatsByConcertId(concertId);
    }

}
