package com.hhplus.concert.domain.waiting;

import com.hhplus.concert.domain.waiting.WaitingException.WaitingError;
import com.hhplus.concert.domain.waiting.WaitingInfo.TokenInfo;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRepository waitingRepository;

    @Transactional
    public WaitingInfo.Created issue() {
        WaitingEntity waiting = WaitingEntity.create(LocalDateTime.now());
        waitingRepository.save(waiting);
        return new WaitingInfo.Created(waiting);
    }

    @Transactional(readOnly = true)
    public WaitingInfo.TokenInfo getToken(String token) {
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

    @Scheduled(fixedDelay = 1500, initialDelay = 2000)
    @Transactional
    public void activate() {
        List<WaitingEntity> targets = waitingRepository.findActivateTargets(WaitingStatus.WAIT, WaitingEntity.ACTIVATE_PERSONNEL);
        if (targets.isEmpty()) {
            throw new WaitingException(WaitingError.ACTIVATE_TARGET_NOT_FOUND);
        }
        targets.forEach(WaitingEntity::activate);
    }

}
