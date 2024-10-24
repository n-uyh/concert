package com.hhplus.concert.domain.point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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

}
