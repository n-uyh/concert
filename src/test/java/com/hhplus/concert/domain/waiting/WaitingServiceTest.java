package com.hhplus.concert.domain.waiting;

import static org.junit.jupiter.api.Assertions.*;

import com.hhplus.concert.domain.waiting.WaitingInfo.CreatedInfo;
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
}
