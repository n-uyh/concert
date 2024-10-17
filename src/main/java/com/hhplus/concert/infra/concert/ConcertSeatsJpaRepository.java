package com.hhplus.concert.infra.concert;

import com.hhplus.concert.domain.concert.ConcertSeatEntity;
import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConcertSeatsJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {

    List<ConcertSeatEntity> findAllByConcertId(long concertId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from ConcertSeatEntity s where s.id = :seatId")
    ConcertSeatEntity findOneByIdWithLock(@Param("seatId") long seatId);
}
