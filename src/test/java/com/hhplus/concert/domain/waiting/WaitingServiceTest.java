package com.hhplus.concert.domain.waiting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.hhplus.concert.api.waiting.WaitingResponse.CheckedResult;
import com.hhplus.concert.api.waiting.WaitingResponse.CreatedResult;
import com.hhplus.concert.domain.user.UserRepository;
import com.hhplus.concert.domain.waiting.WaitingException.WaitingError;
import java.time.LocalDateTime;
import java.util.List;
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

    @Test
    @DisplayName("대기열 토큰의 상태를 체크할 때, 토큰을 찾을 수 없는 경우 TOKEN_NOT_FOUND 에러가 발생한다.")
    void whenCheckTokenStatusThenTokenNotFound() {
        String token = "someToken";
        when(waitingRepository.findOneByToken(token)).thenReturn(null);

        WaitingException exception = assertThrows(WaitingException.class,
            () -> waitingService.checkTokenStatus(token));

        assertEquals(WaitingError.TOKEN_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("대기열 토큰의 상태를 체크할 때, 토큰이 만료된 경우 EXPIRED_TOKEN 에러가 발생한다.")
    void whenCheckTokenStatusThenExpiredToken() {
        String token = "someToken";
        WaitingStatus expired = WaitingStatus.EXPIRED;
        LocalDateTime someDateTime = LocalDateTime.of(2024,10,10,20,0,0);
        when(waitingRepository.findOneByToken(token)).thenReturn(
            new WaitingEntity(1,1,token,expired,someDateTime,someDateTime)
        );

        WaitingException exception = assertThrows(WaitingException.class,
            () -> waitingService.checkTokenStatus(token));

        assertEquals(WaitingError.EXPIRED_TOKEN, exception.getErrorCode());
    }

    @Test
    @DisplayName("대기열 토큰의 상태를 체크할 때, 토큰이 ACTIVE 상태이면 그대로 반환한다.")
    void whenCheckTokenStatusAndReturnAlreadyActive() {
        String token = "someToken";
        WaitingStatus active = WaitingStatus.ACTIVE;
        LocalDateTime someDateTime = LocalDateTime.of(2024,10,10,20,0,0);

        when(waitingRepository.findOneByToken(token)).thenReturn(
            new WaitingEntity(1,1,token,active,someDateTime,someDateTime)
        );

        CheckedResult result = waitingService.checkTokenStatus(token);
        assertEquals(token, result.token());
        assertEquals(active.name(), result.status());
    }

    @Test
    @DisplayName("대기열 토큰의 상태를 체크할 때, 토큰상태가 WAITING이고 ACTIVE 토큰의 수가 최대이면 대기상태와 대기순번을 반환한다.")
    void whenCheckTokenStatusAndActiveFullThenStillWaiting() {
        String token = "someToken";
        WaitingStatus status = WaitingStatus.WAITING;
        LocalDateTime someDateTime = LocalDateTime.of(2024,10,10,20,0,0);
        WaitingEntity waiting = new WaitingEntity(1, 1, token, status, someDateTime,
            someDateTime);
        int max = WaitingEntity.MAX_ACTIVE;

        when(waitingRepository.findOneByToken(token)).thenReturn(waiting);
        when(waitingRepository.countAllStatusActive()).thenReturn(max);
        when(waitingRepository.findAllStatusWaiting()).thenReturn(
            List.of(
                waiting,
                new WaitingEntity(2, 2, "otherToken", status, someDateTime,
                    someDateTime),
                new WaitingEntity(3, 3, "otherToken2", status, someDateTime,
                    someDateTime)
            )
        );

        CheckedResult result = waitingService.checkTokenStatus(token);
        assertEquals(token, result.token());
        assertEquals(status.name(), result.status());
        assertEquals(1, result.waitingNo());
    }

    @Test
    @DisplayName("대기열 토큰의 상태를 체크할 때, 토큰상태가 WAITING이고 ACTIVE TO가 있고 대기 순번이 TO보다 크면 현재의 대기상태와 대기순번을 반환한다.")
    void whenCheckTokenStatusAndActiveNotFullAndWaitingNoIsBiggerThanTO_ThenStillWaiting() {
        String token = "someToken";
        WaitingStatus status = WaitingStatus.WAITING;
        LocalDateTime someDateTime = LocalDateTime.of(2024,10,10,20,0,0);
        WaitingEntity waiting = new WaitingEntity(1, 1, token, status, someDateTime,
            someDateTime);
        int max = WaitingEntity.MAX_ACTIVE;

        when(waitingRepository.findOneByToken(token)).thenReturn(waiting);
        when(waitingRepository.countAllStatusActive()).thenReturn(max - 2);
        when(waitingRepository.findAllStatusWaiting()).thenReturn(
            List.of(
                new WaitingEntity(2, 2, "otherToken", status, someDateTime,
                    someDateTime),
                new WaitingEntity(3, 3, "otherToken2", status, someDateTime,
                    someDateTime),
                waiting
            )
        );

        CheckedResult result = waitingService.checkTokenStatus(token);

        assertEquals(token, result.token());
        assertEquals(status.name(), result.status());
        assertEquals(3, result.waitingNo());
    }

    @Test
    @DisplayName("대기열 토큰의 상태를 체크할 때, 토큰상태가 WAITING이고 ACTIVE TO가 있고 대기 순번이 TO보다 작으면 토큰을 ACTIVE 상태로 업데이트 한 뒤 반환한다.")
    void whenCheckTokenStatusAndActiveNotFullAndWaitingNoIsLessThanTO_ThenActivateToken() {
        String token = "someToken";
        WaitingStatus status = WaitingStatus.WAITING;
        LocalDateTime someDateTime = LocalDateTime.of(2024,10,10,20,0,0);
        WaitingEntity waiting = new WaitingEntity(1, 1, token, status, someDateTime,
            someDateTime);
        int max = WaitingEntity.MAX_ACTIVE;

        when(waitingRepository.findOneByToken(token)).thenReturn(waiting);
        when(waitingRepository.countAllStatusActive()).thenReturn(max - 2);
        when(waitingRepository.findAllStatusWaiting()).thenReturn(
            List.of(
                waiting,
                new WaitingEntity(2, 2, "otherToken", status, someDateTime,
                    someDateTime),
                new WaitingEntity(3, 3, "otherToken2", status, someDateTime,
                    someDateTime)
            )
        );

        CheckedResult result = waitingService.checkTokenStatus(token);

        assertEquals(token, result.token());
        assertEquals(WaitingStatus.ACTIVE.name(), result.status());
        assertEquals(0, result.waitingNo());
    }

}
