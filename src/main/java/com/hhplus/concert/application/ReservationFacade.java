package com.hhplus.concert.application;

import com.hhplus.concert.domain.concert.ConcertInfo;
import com.hhplus.concert.domain.concert.ConcertService;
import com.hhplus.concert.domain.reservation.ReservationCommand;
import com.hhplus.concert.domain.reservation.ReservationInfo;
import com.hhplus.concert.domain.reservation.ReservationService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

    private final ConcertService concertService;
    private final ReservationService reservationService;

    @Transactional
    public ReservationInfo.ReservedInfo reserveSeat(ReservationCommand.ReserveSeat command) {
        ConcertInfo.SeatInfo seatInfo = concertService.occupySeat(command.seatId());
        return reservationService.reserveSeat(seatInfo, command.userId());
    }

    @Scheduled(fixedDelay = 1000 * 60 * 3, initialDelay = 3000)
    @Transactional
    public void expire() {
        LocalDateTime now = LocalDateTime.now();
        List<Long> seatIds = reservationService.expire(now);
        concertService.releaseSeat(seatIds);
    }

}
