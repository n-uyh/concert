package com.hhplus.concert.domain.waiting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hhplus.concert.domain.waiting.WaitingException.WaitingError;
import com.hhplus.concert.domain.waiting.WaitingInfo.CreatedInfo;
import com.hhplus.concert.domain.waiting.WaitingInfo.TokenInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    @Test
    @DisplayName("대기열토큰을 발급시 토큰의 상태는 WAIT이다.")
    void createTokenThenStatusWait() {
        CreatedInfo token = waitingService.createToken();
        assertEquals(WaitingStatus.WAIT.name(), token.status());
    }

    @Test
    @DisplayName("대기열 토큰의 상태를 체크할 때, 토큰을 찾을 수 없는 경우 TOKEN_NOT_FOUND 에러가 발생한다.")
    void getTokenWithWaitingNoAndTokenNotFound() {
        String token = "someToken";
        when(waitingRepository.findOneByToken(token)).thenReturn(Optional.empty());

        WaitingException exception = assertThrows(WaitingException.class,
            () -> waitingService.getTokenWithWaitingNo(token));

        assertEquals(WaitingError.TOKEN_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("대기열 토큰의 상태를 체크할 때, 토큰이 만료된 경우 EXPIRED_TOKEN 에러가 발생한다.")
    void getTokenWithWaitingNoAndTokenNotFoundExpiredToken() {
        String token = "someToken";
        WaitingStatus expired = WaitingStatus.EXPIRED;
        LocalDateTime someDateTime = LocalDateTime.of(2024,10,10,20,0,0);
        when(waitingRepository.findOneByToken(token)).thenReturn(
            Optional.of(new WaitingEntity(1, token, expired, someDateTime, someDateTime))
        );

        WaitingException exception = assertThrows(WaitingException.class,
            () -> waitingService.getTokenWithWaitingNo(token));

        assertEquals(WaitingError.EXPIRED_TOKEN, exception.getErrorCode());
    }

    @Test
    @DisplayName("대기열 토큰의 상태를 체크할 때, 토큰이 ACTIVE 상태이면 그대로 반환한다.")
    void whenCheckTokenStatusAndReturnAlreadyActive() {
        String token = "someToken";
        WaitingStatus active = WaitingStatus.ACTIVE;
        LocalDateTime someDateTime = LocalDateTime.of(2024,10,10,20,0,0);

        when(waitingRepository.findOneByToken(token)).thenReturn(
            Optional.of(new WaitingEntity(1, token, active, someDateTime, someDateTime))
        );

        TokenInfo result = waitingService.getTokenWithWaitingNo(token);
        assertEquals(token, result.token());
        assertEquals(active.name(), result.status());
        assertEquals(0, result.waitingNo());
    }

    @Test
    @DisplayName("대기열 토큰의 상태를 체크할 때, 토큰상태가 WAIT이면 대기순번을 반환한다.")
    void whenCheckTokenStatusAndActiveFullThenStillWaiting() {
        String token = "someToken";
        WaitingStatus status = WaitingStatus.WAIT;
        LocalDateTime someDateTime = LocalDateTime.of(2024,10,10,20,0,0);
        WaitingEntity waiting = new WaitingEntity(1,  token, status, someDateTime,
            someDateTime);

        when(waitingRepository.findOneByToken(token)).thenReturn(Optional.of(waiting));
        when(waitingRepository.findAllStatusWaiting()).thenReturn(
            List.of(
                waiting,
                new WaitingEntity(2, "otherToken", status, someDateTime,
                    someDateTime),
                new WaitingEntity(3, "otherToken2", status, someDateTime,
                    someDateTime)
            )
        );

        TokenInfo result = waitingService.getTokenWithWaitingNo(token);
        assertEquals(token, result.token());
        assertEquals(status.name(), result.status());
        assertEquals(1, result.waitingNo());
    }

    @Test
    @DisplayName("토큰이 ACTIVE 상태인지 확인할때 발급된 토큰이 없는 경우 TOKEN_NOT_FOUND 에러가 발생한다")
    void checkTokenIsActiveButNotFound() {
        String token = "someToken";
        when(waitingRepository.findOneByToken(token)).thenReturn(Optional.empty());
        WaitingException exception = assertThrows(WaitingException.class,
            () -> waitingService.checkTokenIsActive(token));

        assertEquals(WaitingError.TOKEN_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("토큰이 ACTIVE 상태인지 확인할때 발급된 토큰이 ACTIVE상태가 아닌경우 NOT_ACTIVE_TOKEN 에러가 발생한다")
    void checkTokenIsActiveButNotActive() {
        String token = "someToken";
        WaitingStatus status = WaitingStatus.WAIT;
        LocalDateTime someDateTime = LocalDateTime.of(2024,10,10,20,0,0);
        WaitingEntity waiting = new WaitingEntity(1,  token, status, someDateTime,
            someDateTime);

        when(waitingRepository.findOneByToken(token)).thenReturn(Optional.of(waiting));
        WaitingException exception = assertThrows(WaitingException.class,
            () -> waitingService.checkTokenIsActive(token));

        assertEquals(WaitingError.NOT_ACTIVE_TOKEN, exception.getErrorCode());
    }

    @Test
    @DisplayName("토큰이 ACTIVE 상태인지 확인할때 발급된 토큰이 ACTIVE상태이면 아무 에러도 발생하지 않는다")
    void checkTokenIsActiveAndActive() {
        String token = "someToken";
        WaitingStatus status = WaitingStatus.ACTIVE;
        LocalDateTime someDateTime = LocalDateTime.of(2024,10,10,20,0,0);
        WaitingEntity waiting = new WaitingEntity(1,  token, status, someDateTime,
            someDateTime);

        when(waitingRepository.findOneByToken(token)).thenReturn(Optional.of(waiting));

        waitingService.checkTokenIsActive(token);

        verify(waitingRepository, times(1)).findOneByToken(token);
    }

    @Test
    @DisplayName("토큰 만료 성공테스트")
    void expireTokenSuccess() {
        String token = "someToken";
        LocalDateTime createdAt = LocalDateTime.of(2024, 10, 10, 12, 0, 0);
        WaitingEntity entity = new WaitingEntity(0, token, WaitingStatus.ACTIVE, createdAt,
            createdAt);
        when(waitingRepository.findOneByToken(token)).thenReturn(Optional.of(entity));

        waitingService.expireToken(token);

        assertEquals(WaitingStatus.EXPIRED, entity.getStatus());
    }
}
