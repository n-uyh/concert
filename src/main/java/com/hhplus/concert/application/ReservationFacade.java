package com.hhplus.concert.application;

import com.hhplus.concert.domain.concert.ConcertInfo.SeatInfo;
import com.hhplus.concert.domain.reservation.ReservationInfo.ReservedInfo;
import com.hhplus.concert.domain.concert.ConcertService;
import com.hhplus.concert.domain.reservation.ReservationCommand;
import com.hhplus.concert.domain.reservation.ReservationService;
import com.hhplus.concert.domain.waiting.WaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

    private final ConcertService concertService;
    private final ReservationService reservationService;

    @Transactional
    public ReservedInfo reserveSeat(ReservationCommand.ReserveSeat command) {
        SeatInfo seatInfo = concertService.findAndOccupySeat(command.seatId());
        return reservationService.reserveSeat(seatInfo, command.userId());
    }

}
