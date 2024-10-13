package com.hhplus.concert.domain.waiting;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.hhplus.concert.api.waiting.WaitingResponse;
import com.hhplus.concert.api.waiting.WaitingResponse.CreatedResult;
import com.hhplus.concert.domain.user.UserRepository;
import com.hhplus.concert.domain.waiting.WaitingException.WaitingError;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("대기열 단위테스트")
@ExtendWith(MockitoExtension.class)
class WaitingServiceTest {

    @InjectMocks
    private WaitingService waitingService;

    @Mock
    private WaitingRepository waitingRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("대기열토큰을 발급/조회 할 때 사용자 id로 사용자를 찾을 수 없는 경우는 USER_NOT_FOUND에러가 발생한다.")
    void whenCreateOrGetTokenButNoUser() {
        long userId = 1;
        when(userRepository.existsByUserId(userId)).thenReturn(false);

        WaitingException exception = assertThrows(WaitingException.class,
            () -> waitingService.createOrGetToken(userId));

        assertEquals(WaitingError.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("대기열토큰을 발급/조회 할 때 기존발급건이 없는 경우 리턴값의 userId는 최초에 요청한 userId와 일치한다.")
    void whenCreateOrGetTokenAndSuccessfullyCreateNewToken() {
        long userId = 1;
        when(userRepository.existsByUserId(userId)).thenReturn(true);
        when(waitingRepository.findNotExpiredByUserId(userId)).thenReturn(null);

        CreatedResult response = waitingService.createOrGetToken(userId);
        assertEquals(userId, response.userId());
    }

    @Test
    @DisplayName("대기열토큰을 발급/조회 할때 기존발급건이 있으면 기존 발급건을 리턴한다.")
    void whenCreateOrGetTokenAndReturnAlreadyCreatedToken() {
        long userId = 1;
        LocalDateTime createdAt = LocalDateTime.of(2024,10,10,20,0,0);
        String token = UUID.randomUUID().toString();
        when(userRepository.existsByUserId(userId)).thenReturn(true);
        when(waitingRepository.findNotExpiredByUserId(userId)).thenReturn(
            new WaitingEntity(1, userId, token, WaitingStatus.WAITING, createdAt, createdAt)
        );

        CreatedResult response = waitingService.createOrGetToken(userId);
        assertEquals(userId, response.userId());
        assertEquals(token, response.token());
        assertEquals(createdAt, response.createdAt());
    }

}
