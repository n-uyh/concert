package com.hhplus.concert.infra.concert;

import com.hhplus.concert.domain.concert.ConcertEntity;
import com.hhplus.concert.domain.concert.ConcertRepository;
import com.hhplus.concert.domain.concert.ConcertSeatEntity;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;
    private final ConcertSeatsJpaRepository concertSeatsJpaRepository;

    @Override
    public List<ConcertEntity> findAllByConcertDateBetween(LocalDate from, LocalDate end) {
        return concertJpaRepository.findAllByConcertDateBetween(from, end);
    }

    @Override
    public List<ConcertSeatEntity> findAllSeatsByConcertId(long concertId) {
        return concertSeatsJpaRepository.findAllByConcertId(concertId);
    }
}
