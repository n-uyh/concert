package com.hhplus.concert.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.hhplus.concert.application.UserInfo.Point;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("유저서비스 단위테스트")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("사용자가 포인트를 충전한 경우 잔액은 (기존 포인트 + 충전금액)이다.")
    void userPointChargeSuccess() {
        long userId = 1;
        long amount = 20_000;
        when(userRepository.findOneUser(userId)).thenReturn(
            Optional.of(new UserEntity(userId, 100_000))
        );

        Point point = userService.chargePoint(userId, amount);

        assertEquals(userId, point.userId());
        assertEquals(120_000, point.point());
    }

    @Test
    @DisplayName("사용자 포인트 잔액조회 성공케이스")
    void getUserPointSuccess() {
        long userId = 1;
        when(userRepository.findOneUser(userId)).thenReturn(
            Optional.of(new UserEntity(userId, 100_000))
        );

        Point point = userService.getPoint(userId);

        assertEquals(userId, point.userId());
        assertEquals(100_000, point.point());
    }

}
