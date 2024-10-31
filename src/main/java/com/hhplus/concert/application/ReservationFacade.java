package com.hhplus.concert.application;

import com.hhplus.concert.domain.concert.ConcertException;
import com.hhplus.concert.domain.concert.ConcertException.ConcertError;
import com.hhplus.concert.domain.concert.ConcertInfo;
import com.hhplus.concert.domain.concert.ConcertService;
import com.hhplus.concert.domain.reservation.ReservationCommand;
import com.hhplus.concert.domain.reservation.ReservationInfo;
import com.hhplus.concert.domain.reservation.ReservationService;
import com.hhplus.concert.infra.redis.RedisLockRepository;
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
    private final RedisLockRepository redisLockRepository;

    public ReservationInfo.ReservedInfo reserveSeat(ReservationCommand.ReserveSeat command) {
        long seatId = command.seatId();
        Boolean lock = redisLockRepository.lock(seatId);
        if (lock) {
            try {
                ConcertInfo.SeatInfo seatInfo = concertService.occupySeat(seatId);
                return reservationService.reserveSeat(seatInfo, command.userId());
            } finally {
                redisLockRepository.unlock(seatId);
            }
        } else {
            throw new ConcertException(ConcertError.SEAT_ALREADY_OCCUPIED);
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 3, initialDelay = 3000)
    @Transactional
    public void expire() {
        LocalDateTime now = LocalDateTime.now();
        List<Long> seatIds = reservationService.expire(now);
        concertService.releaseSeat(seatIds);
    }

}
