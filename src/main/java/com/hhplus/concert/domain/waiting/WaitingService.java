package com.hhplus.concert.domain.waiting;

import com.hhplus.concert.domain.waiting.WaitingException.WaitingError;
import com.hhplus.concert.infra.redis.waiting.WaitingParam;
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

    public WaitingInfo.Created issue() {
        WaitingToken token = WaitingToken.issue(LocalDateTime.now());
        waitingRepository.issue(WaitingParam.Issue.of(token));
        return WaitingInfo.Created.of(token);
    }

    public WaitingInfo.TokenInfo getToken(String token) {
        WaitingParam.Search param = new WaitingParam.Search(token);
        WaitingInfo.TokenInfo result = waitingRepository.findWaitToken(param);

        if (result == null) {
            result = waitingRepository.findActiveToken(param);

            if (result == null) {
                throw new WaitingException(WaitingError.TOKEN_NOT_FOUND);
            }
        }

        return result;
    }

    public void checkTokenIsActive(String token) {
        WaitingInfo.TokenInfo activeToken = waitingRepository.findActiveToken(new WaitingParam.Search(token));
        if (activeToken == null) {
            throw new WaitingException(WaitingError.NOT_ACTIVE_TOKEN);
        }
    }

    public void expireToken(String token) {
        waitingRepository.expire(new WaitingParam.Search(token));
    }

    @Scheduled(fixedDelay = 3000, initialDelay = 2000)
    public void activate() {
        List<WaitingInfo.ActivateTarget> targets = waitingRepository.findActivateTargets(new WaitingParam.ActivateTarget(WaitingToken.ACTIVATE_PERSONNEL));
        if (targets.isEmpty()) {
            throw new WaitingException(WaitingError.ACTIVATE_TARGET_NOT_FOUND);
        }
        waitingRepository.activate(WaitingParam.Activate.of(targets, WaitingToken.ACTIVE_MINUTE, WaitingToken.ACTIVE_TIMEUNIT));
    }

}
