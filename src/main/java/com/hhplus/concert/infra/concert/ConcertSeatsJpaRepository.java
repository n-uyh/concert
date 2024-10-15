package com.hhplus.concert.infra.concert;

import com.hhplus.concert.domain.concert.ConcertSeatEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertSeatsJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {

    List<ConcertSeatEntity> findAllByConcertId(long concertId);

}
