package com.hhplus.concert.infra.concert;

import com.hhplus.concert.domain.concert.ConcertEntity;
import com.hhplus.concert.domain.concert.ConcertRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public List<ConcertEntity> findAllByConcertDateBetween(LocalDate from, LocalDate end) {
        return concertJpaRepository.findAllByConcertDateBetween(from, end);
    }
}
