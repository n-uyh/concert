package com.hhplus.concert.infra.reservation;

import com.hhplus.concert.domain.reservation.ReservationEntity;
import com.hhplus.concert.domain.reservation.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findAllByStatusAndExpiredAtBefore(ReservationStatus status, LocalDateTime expiredAt);

}
