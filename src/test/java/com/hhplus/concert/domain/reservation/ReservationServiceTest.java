package com.hhplus.concert.domain.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hhplus.concert.application.ConcertInfo.SeatInfo;
import com.hhplus.concert.application.ReservationInfo.ReservedInfo;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("예약서비스 단위테스트")
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    ReservationRepository reservationRepository;

    @Test
    @DisplayName("좌석예약생성 성공시 상태는 RESERVED이고, 예약생성시간과 만료예정시간의 차이는 EXPIRES_AFTER_SECONDS와 같다.")
    void createReservationSuccess() {
        long seatId = 1;
        long userId = 1;
        long price = 100_000;
        boolean seatOccupied = false;

        ReservedInfo info = reservationService.reserveSeat(
            new SeatInfo(seatId, 1, price, seatOccupied), userId);
        verify(reservationRepository, times(1)).insertOne(any(ReservationEntity.class));

        assertEquals(seatId, info.seatId());
        assertEquals(ReservationStatus.RESERVED.name(), info.status());
        assertEquals(ReservationEntity.EXPIRES_AFTER_SECONDS, Duration.between(info.reservedAt(), info.expiresAt()).getSeconds());
    }

    @Test
    @DisplayName("예약건을 찾고 만료정보 업데이트 성공케이스")
    void findReservationWithStatusUpdate() {
        long reservationId = 1;
        LocalDateTime createdAt = LocalDateTime.of(2024, 10, 10, 15, 0, 0);
        when(reservationRepository.findOneById(reservationId)).thenReturn(
            new ReservationEntity(reservationId, 1, 1, 100_000, ReservationStatus.RESERVED, createdAt, createdAt.plusMinutes(5))
        );

        ReservedInfo info = reservationService.findReservationWithStatusUpdate(
            reservationId);

        assertEquals(ReservationStatus.CANCELED.name(),info.status());
    }
}
