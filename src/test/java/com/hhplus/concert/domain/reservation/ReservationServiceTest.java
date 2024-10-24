package com.hhplus.concert.domain.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hhplus.concert.domain.concert.ConcertInfo.SeatInfo;
import com.hhplus.concert.domain.reservation.ReservationException.ReserveError;
import com.hhplus.concert.domain.reservation.ReservationInfo.ReservedInfo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
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

    @Test
    @DisplayName("예약만료 시 만료 대상 예약건들이 없는 경우, EXPIRE_TARGETS_NOT_FOUND 에러가 발생한다.")
    void expireReservationButTargetsNotFound() {
        ReservationStatus status = ReservationStatus.RESERVED;
        LocalDateTime baseTime = LocalDateTime.of(2024,10,10,15,0,2);

        when(reservationRepository.findExpireTargets(status, baseTime)).thenReturn(List.of());

        ReservationException exception = assertThrows(ReservationException.class,
            () -> reservationService.expire(baseTime));

        assertEquals(ReserveError.EXPIRE_TARGETS_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("예약만료대상을 성공적으로 만료시키는 경우, 만료 대상 예약 건들의 상태가 RESERVED -> CANCEL로 업데이트 된다.")
    void expireReservationSuccessThenReservationStatusWillBeCancel() {
        ReservationStatus status = ReservationStatus.RESERVED;
        LocalDateTime createdAt = LocalDateTime.of(2024, 10, 10, 14, 55, 0);
        LocalDateTime baseTime = LocalDateTime.of(2024,10,10,15,0,2);

        List<ReservationEntity> targets = List.of(
            new ReservationEntity(1, 1, 1, 100_000, status, createdAt, createdAt.plusMinutes(5)),
            new ReservationEntity(2, 2, 2, 100_000, status, createdAt, createdAt.plusMinutes(5)),
            new ReservationEntity(3, 3, 3, 100_000, status, createdAt, createdAt.plusMinutes(5))
        );
        when(reservationRepository.findExpireTargets(status, baseTime)).thenReturn(targets);

        List<Long> seatIds = reservationService.expire(baseTime);

        assertEquals(3, seatIds.size());
        assertEquals(ReservationStatus.CANCELED, targets.get(0).getStatus());
    }
}
