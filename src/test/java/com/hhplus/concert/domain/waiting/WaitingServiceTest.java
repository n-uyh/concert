package com.hhplus.concert.domain.waiting;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hhplus.concert.domain.waiting.WaitingException.WaitingError;
import com.hhplus.concert.domain.waiting.WaitingInfo.Created;
import com.hhplus.concert.domain.waiting.WaitingInfo.TokenInfo;
import com.hhplus.concert.infra.redis.waiting.WaitingParam;
import com.hhplus.concert.infra.redis.waiting.WaitingParam.ActivateTarget;
import java.time.LocalDateTime;
import java.util.List;
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
    void issueThenStatusWait() {
        Created token = waitingService.issue();
        assertEquals(WaitingStatus.WAIT.name(), token.status());
    }

    @Test
    @DisplayName("대기열 토큰을 조회할 때, WAIT/ACTIVE 대기열 두곳에서 모두 찾을 수 없으면 TOKEN_NOT_FOUND 에러가 발생한다.")
    void getTokenButTokenNotFound() {
        String token = "someToken";
        WaitingParam.Search param = new WaitingParam.Search(token);

        when(waitingRepository.findWaitToken(param)).thenReturn(null);
        when(waitingRepository.findActiveToken(param)).thenReturn(null);

        WaitingException exception = assertThrows(WaitingException.class,
            () -> waitingService.getToken(token));

        assertEquals(WaitingError.TOKEN_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("대기열 토큰을 조회할 때, WAIT 대기열에서 토큰을 발견하면 WAIT상태와 대기순번을 알 수 있다.")
    void getTokenAndStatusWait() {
        String token = "someToken";
        WaitingParam.Search param = new WaitingParam.Search(token);

        when(waitingRepository.findWaitToken(param)).thenReturn(WaitingInfo.TokenInfo.waiting(token,20L));

        TokenInfo tokenInfo = waitingService.getToken(token);
        assertEquals(WaitingStatus.WAIT.name(), tokenInfo.status());
        assertEquals(20L, tokenInfo.waitingNo());
    }

    @Test
    @DisplayName("대기열 토큰을 조회할 때, WAIT 대기열에서 토큰을 발견하지 못하였으나 ACTIVE 대기열에서 토큰을 발견하면 토큰은 ACTIVE 상태이고, 대기순번은 0이다.")
    void getTokenAndStatusActive() {
        String token = "someToken";
        WaitingParam.Search param = new WaitingParam.Search(token);

        when(waitingRepository.findWaitToken(param)).thenReturn(null);
        when(waitingRepository.findActiveToken(param)).thenReturn(WaitingInfo.TokenInfo.acitve(token));

        TokenInfo tokenInfo = waitingService.getToken(token);
        assertEquals(WaitingStatus.ACTIVE.name(), tokenInfo.status());
        assertEquals(0, tokenInfo.waitingNo());
    }

    @Test
    @DisplayName("토큰이 ACTIVE 상태인지 확인할때 ACTIVE 대기열에서 토큰을 찾지 못할 경우 NOT_ACTIVE_TOKEN 에러가 발생한다")
    void checkTokenIsActiveButNotActive() {
        String token = "someToken";
        WaitingParam.Search param = new WaitingParam.Search(token);
        when(waitingRepository.findActiveToken(param)).thenReturn(null);
        WaitingException exception = assertThrows(WaitingException.class,
            () -> waitingService.checkTokenIsActive(token));

        assertEquals(WaitingError.NOT_ACTIVE_TOKEN, exception.getErrorCode());
    }


    @Test
    @DisplayName("토큰이 ACTIVE 상태인지 확인할때 발급된 토큰이 ACTIVE상태이면 아무 에러도 발생하지 않는다")
    void checkTokenIsActiveAndActive() {
        String token = "someToken";
        WaitingParam.Search param = new WaitingParam.Search(token);

        when(waitingRepository.findActiveToken(param)).thenReturn(WaitingInfo.TokenInfo.acitve(token));

        assertDoesNotThrow(()->waitingService.checkTokenIsActive(token));

        verify(waitingRepository, times(1)).findActiveToken(param);
    }

    @Test
    @DisplayName("토큰 만료 내부로직 호출 테스트")
    void expireTokenSuccess() {
        String token = "someToken";
        WaitingParam.Search param = new WaitingParam.Search(token);

        waitingService.expireToken(token);

        verify(waitingRepository, times(1)).expire(param);
    }

    @Test
    @DisplayName("대기열 토큰 스케줄러 실행 중 활성화 대상 토큰이 없는 경우 ACTIVATE_TARGET_NOT_FOUND 에러가 발생한다.")
    void tokenActivateSchedulerAndActivateTargetNotFound() {
        long personnel = WaitingToken.ACTIVATE_PERSONNEL;
        WaitingParam.ActivateTarget param = new WaitingParam.ActivateTarget(personnel);
        when(waitingRepository.findActivateTargets(param)).thenReturn(
            List.of()
        );

        WaitingException exception = assertThrows(WaitingException.class,
            () -> waitingService.activate());

        assertEquals(WaitingError.ACTIVATE_TARGET_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("대기열 토큰 스케줄러 실행 중 활성화 대상 토큰을 잘 조회해온다면 업데이트 로직이 실행된다.")
    void tokenActivateSchedulerThenTargetsStatusChangedToActive() {
        long personnel = WaitingToken.ACTIVATE_PERSONNEL;

        List<WaitingInfo.ActivateTarget> targets = List.of(
            new WaitingInfo.ActivateTarget("token1"),
            new WaitingInfo.ActivateTarget("token2"),
            new WaitingInfo.ActivateTarget("token3")
        );

        WaitingParam.ActivateTarget param = new WaitingParam.ActivateTarget(personnel);
        when(waitingRepository.findActivateTargets(param)).thenReturn(targets);

        waitingService.activate();

        List<WaitingParam.Activate> activeParams = WaitingParam.Activate.of(targets, WaitingToken.ACTIVE_MINUTE, WaitingToken.ACTIVE_TIMEUNIT);
        verify(waitingRepository, times(1)).activate(activeParams);
    }
}
