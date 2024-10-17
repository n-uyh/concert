package com.hhplus.concert.domain.reservation;

import com.hhplus.concert.application.ConcertInfo.SeatInfo;
import com.hhplus.concert.application.ReservationInfo.ReservedInfo;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservedInfo reserveSeat(SeatInfo seatInfo, long userId) {
        ReservationEntity entity = ReservationEntity.create(seatInfo.seatId(), userId,
            seatInfo.price(),  LocalDateTime.now());
        reservationRepository.insertOne(entity);

        return ReservedInfo.from(entity);
    }

}
