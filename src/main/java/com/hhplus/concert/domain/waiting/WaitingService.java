package com.hhplus.concert.domain.waiting;

import com.hhplus.concert.domain.waiting.WaitingException.WaitingError;
import com.hhplus.concert.domain.waiting.WaitingInfo.CreatedInfo;
import com.hhplus.concert.domain.waiting.WaitingInfo.TokenInfo;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRepository waitingRepository;

    @Transactional
    public CreatedInfo createToken() {
        WaitingEntity waiting = WaitingEntity.create(LocalDateTime.now());
        waitingRepository.insertOne(waiting);
        return new CreatedInfo(waiting);
    }

    @Transactional(readOnly = true)
    public TokenInfo getTokenWithWaitingNo(String token) {
        WaitingEntity waiting = waitingRepository.findOneByToken(token).orElseThrow(
            () -> new WaitingException(WaitingError.TOKEN_NOT_FOUND));

        waiting.checkExpired();

        if (waiting.isActive()) {
            return new TokenInfo(waiting,0);
        }

        List<WaitingEntity> waitings = waitingRepository.findAllStatusWaiting();

        int waitingNo = waitings.indexOf(waiting) + 1;
        return new TokenInfo(waiting, waitingNo);
    }

    @Transactional(readOnly = true)
    public void checkTokenIsActive(String token) {
        WaitingEntity waiting = waitingRepository.findOneByToken(token).orElseThrow(() -> new WaitingException(WaitingError.TOKEN_NOT_FOUND));
        if (!waiting.isActive()) {
            throw new WaitingException(WaitingError.NOT_ACTIVE_TOKEN);
        }
    }

    @Transactional
    public void expireToken(String token) {
        WaitingEntity waiting = waitingRepository.findOneByToken(token).orElseThrow(() -> new WaitingException(WaitingError.TOKEN_NOT_FOUND));
        waiting.expire();
    }
}
