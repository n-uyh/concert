package com.hhplus.concert.domain.point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.hhplus.concert.domain.point.PointException.PointError;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("포인트서비스 단위테스트")
@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @InjectMocks
    PointService pointService;

    @Mock
    PointRepository pointRepository;

    @Test
    @DisplayName("사용자가 포인트를 충전한 경우 잔액은 (기존 포인트 + 충전금액)이다.")
    void userPointChargeSuccess() {
        long userId = 1;
        long amount = 20_000;
        when(pointRepository.findUserPoint(userId)).thenReturn(
            Optional.of(new PointEntity(1, userId, 100_000))
        );

        PointInfo.Common point = pointService.chargePoint(new PointCommand.Charge(userId, amount));

        assertEquals(userId, point.userId());
        assertEquals(120_000, point.point());
    }

    @Test
    @DisplayName("사용자 포인트 잔액조회 성공 시 사용자의 포인트 정보를 잘 가져온다.")
    void getUserPointSuccess() {
        long userId = 1;
        when(pointRepository.findUserPoint(userId)).thenReturn(
            Optional.of(new PointEntity(1,userId, 100_000))
        );

        PointInfo.Common point = pointService.getPoint(new PointCommand.Get(userId));

        assertEquals(userId, point.userId());
        assertEquals(100_000, point.point());
    }

    @Test
    @DisplayName("포인트로 결제를 시도했으나 포인트 정보를 찾을 수 없으면 USER_POINT_NOT_FOUND 에러가 발생한다.")
    void payByPointButPointNotFound() {
        long userId = 1;
        when(pointRepository.findUserPointWithLock(userId)).thenReturn(null);

        PointException exception = assertThrows(PointException.class,
            () -> pointService.pay(new PointCommand.Pay(userId, 50_000)));

        assertEquals(PointError.USER_POINT_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("포인트로 결제를 시도했으나 결제금액보다 포인트 잔액이 적은 경우 INSUFFICIENT_POINT_ERROR 에러가 발생한다.")
    void payByPointButInsufficientPointError() {
        long userId = 1;
        when(pointRepository.findUserPointWithLock(userId)).thenReturn(new PointEntity(1,userId, 40_000));

        PointException exception = assertThrows(PointException.class,
            () -> pointService.pay(new PointCommand.Pay(userId, 50_000)));

        assertEquals(PointError.INSUFFICIENT_POINT_ERROR, exception.getErrorCode());
    }

    @Test
    @DisplayName("포인트로 결제를 시도 시 사용자의 포인트 잔액이 충분한 경우 사용자의 포인트가 결제금액만큼 차감된다.")
    void payByPointAndSuccess() {
        long userId = 1;
        PointEntity point = new PointEntity(1, userId, 110_000);
        when(pointRepository.findUserPointWithLock(userId)).thenReturn(point);
        pointService.pay(new PointCommand.Pay(userId, 50_000));

        assertEquals(60_000, point.getPoint());
    }

}
