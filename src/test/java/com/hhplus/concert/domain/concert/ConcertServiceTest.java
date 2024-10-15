package com.hhplus.concert.domain.concert;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.hhplus.concert.application.ConcertInfo.Common;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("콘서트서비스 단위테스트")
@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @InjectMocks
    ConcertService concertService;

    @Mock
    ConcertRepository concertRepository;

    @Test
    @DisplayName("예약가능한 날짜 조회 시 from ~ end 사이에 있는 공연을 반환한다.")
    void findAllAvailableConcertBySchedule() {
        LocalDate from = LocalDate.of(2024, 10, 1);
        LocalDate end = LocalDate.of(2024, 10, 10);

        LocalDate concertDate = LocalDate.of(2024, 10, 2);
        LocalTime startTime = LocalTime.of(17, 0, 0);

        when(concertRepository.findAllByConcertDateBetween(from, end)).thenReturn(
            List.of(
                new ConcertEntity(1, "공연1", concertDate, startTime)
            )
        );

        List<Common> results = concertService.findAllAvailableConcertBetweenFromAndTo(
            from, end);

        assertEquals(1, results.size());
        assertTrue(concertDate.isAfter(from));
        assertTrue(concertDate.isBefore(end));
    }


}