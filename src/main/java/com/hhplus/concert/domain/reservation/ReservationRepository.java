package com.hhplus.concert.domain.reservation;

public interface ReservationRepository {

    void insertOne(ReservationEntity entity);

    ReservationEntity findOneById(long reservationId);
}
