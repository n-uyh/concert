package com.hhplus.concert.domain.reservation;

import com.hhplus.concert.domain.concert.ConcertInfo.SeatInfo;
import com.hhplus.concert.domain.reservation.ReservationException.ReserveError;
import com.hhplus.concert.domain.reservation.ReservationInfo.ReservedInfo;
import java.time.LocalDateTime;
import java.util.List;
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

        return ReservedInfo.of(entity);
    }

    @Transactional
    public ReservedInfo findReservationWithStatusUpdate(long reservationId) {
        ReservationEntity reservation = reservationRepository.findOneById(reservationId);
        reservation.expire(LocalDateTime.now()); // 만료 시간 지났으면 업데이트
        return ReservedInfo.of(reservation);
    }

    @Transactional
    public List<Long> expire(LocalDateTime baseTime) {
        List<ReservationEntity> targets = reservationRepository.findExpireTargets(
            ReservationStatus.RESERVED, baseTime);

        if (targets.isEmpty()) {
            throw new ReservationException(ReserveError.EXPIRE_TARGETS_NOT_FOUND);
        }

        targets.forEach((r) -> r.expire(baseTime));
        return targets.stream().map(ReservationEntity::getConcertSeatId).toList();
    }

}
