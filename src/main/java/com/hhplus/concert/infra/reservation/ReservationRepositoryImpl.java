package com.hhplus.concert.infra.reservation;

import com.hhplus.concert.domain.reservation.ReservationEntity;
import com.hhplus.concert.domain.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository jpaRepository;

    @Override
    public void insertOne(ReservationEntity entity) {
        jpaRepository.save(entity);
    }

    @Override
    public ReservationEntity findOneById(long reservationId) {
        return jpaRepository.findById(reservationId).get();
    }
}
