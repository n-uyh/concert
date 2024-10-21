package com.hhplus.concert.domain.concert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.hhplus.concert.domain.concert.ConcertInfo.Common;
import com.hhplus.concert.domain.concert.ConcertInfo.SeatInfo;
import com.hhplus.concert.domain.concert.ConcertException.ConcertError;
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

    @Test
    @DisplayName("좌석 조회 성공 테스트")
    void findAllSeatsByConcertId() {
        long concertId = 1;
        when(concertRepository.findAllSeatsByConcertId(concertId)).thenReturn(
            List.of(
                new ConcertSeatEntity(1, concertId, 1, 100_000, false),
                new ConcertSeatEntity(1, concertId, 2, 100_000, false),
                new ConcertSeatEntity(1, concertId, 3, 100_000, false),
                new ConcertSeatEntity(1, concertId, 4, 150_000, true)
            )
        );

        List<SeatInfo> seats = concertService.findAllSeatsByConcertId(concertId);

        assertEquals(4, seats.size());
    }

    @Test
    @DisplayName("좌석을 조회했으나 이미 선택된 좌석입니다.")
    void findSeatButAlreadyOccupied() {
        long seatId = 1;
        boolean occupied = true;
        when(concertRepository.findOneBySeatIdWithLock(seatId)).thenReturn(
            new ConcertSeatEntity(seatId, 1, 1,100_000, occupied)
        );

        ConcertException exception = assertThrows(ConcertException.class,
            () -> concertService.findAndOccupySeat(seatId));

        assertEquals(ConcertError.SEAT_ALREADY_OCCUPIED, exception.getErrorCode());
    }

    @Test
    @DisplayName("좌석을 찾고 점유성공한다.")
    void findAndOccupySeatSuccess() {
        long seatId = 1;
        boolean occupied = false;
        when(concertRepository.findOneBySeatIdWithLock(seatId)).thenReturn(
            new ConcertSeatEntity(seatId, 1, 1,100_000, occupied)
        );

        SeatInfo seat = concertService.findAndOccupySeat(seatId);

        assertEquals(seatId, seat.seatId());
        assertTrue(seat.occupied());
    }


}
