package com.hhplus.concert.infra.reservation;

import com.hhplus.concert.domain.reservation.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

}
