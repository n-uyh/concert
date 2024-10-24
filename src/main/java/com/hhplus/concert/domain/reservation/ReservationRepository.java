package com.hhplus.concert.domain.reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository {

    void insertOne(ReservationEntity entity);

    ReservationEntity findOneById(long reservationId);

    List<ReservationEntity> findExpireTargets(ReservationStatus reservationStatus, LocalDateTime now);
}
