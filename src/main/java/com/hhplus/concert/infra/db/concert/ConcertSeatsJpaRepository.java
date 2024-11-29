package com.hhplus.concert.infra.db.concert;

import com.hhplus.concert.domain.concert.ConcertSeatEntity;
import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConcertSeatsJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {

    @Query("select s from ConcertSeatEntity s where s.concertId =:concertId order by s.id limit 2000")
    List<ConcertSeatEntity> findAllByConcertId(@Param("concertId") long concertId);

}
