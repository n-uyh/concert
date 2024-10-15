package com.hhplus.concert.domain.concert;

import com.hhplus.concert.application.ConcertInfo;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;

    public List<ConcertInfo.Common> findAllAvailableConcertBetweenFromAndTo(LocalDate from, LocalDate end) {
        List<ConcertEntity> concerts = concertRepository.findAllByConcertDateBetween(
            from, end);

        return concerts.stream().map(ConcertInfo.Common::from).toList();
    }
}
