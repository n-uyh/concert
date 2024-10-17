package com.hhplus.concert.domain.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.hhplus.concert.application.ConcertInfo.SeatInfo;
import com.hhplus.concert.application.ReservationInfo.ReservedInfo;
import java.time.Duration;
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
}
