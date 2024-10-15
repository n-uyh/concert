package com.hhplus.concert.infra.concert;

import com.hhplus.concert.domain.concert.ConcertEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {

    List<ConcertEntity> findAllByConcertDateBetween(LocalDate from, LocalDate to);

}
