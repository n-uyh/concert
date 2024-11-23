package com.hhplus.concert.application;

import com.hhplus.concert.domain.concert.ConcertInfo;
import com.hhplus.concert.domain.concert.ConcertService;
import com.hhplus.concert.domain.reservation.ReservationCommand;
import com.hhplus.concert.domain.reservation.ReservationInfo;
import com.hhplus.concert.domain.reservation.ReservationService;
import com.hhplus.concert.domain.support.lock.DistributedLock;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

    private final ConcertService concertService;
    private final ReservationService reservationService;

    @DistributedLock(key = "'seatId:'+#command.seatId()", waitTime = 0)
    @Transactional
    public ReservationInfo.ReservedInfo reserveSeat(ReservationCommand.ReserveSeat command) {
        long seatId = command.seatId();
        ConcertInfo.SeatInfo seatInfo = concertService.occupySeat(seatId);
        return reservationService.reserveSeat(seatInfo, command.userId());
    }

    @Transactional
    public void expire() {
        LocalDateTime now = LocalDateTime.now();
        List<Long> seatIds = reservationService.expire(now);
        concertService.releaseSeat(seatIds);
    }

}
