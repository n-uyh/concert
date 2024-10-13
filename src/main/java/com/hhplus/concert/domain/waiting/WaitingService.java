package com.hhplus.concert.domain.waiting;

import com.hhplus.concert.api.waiting.WaitingResponse.CheckedResult;
import com.hhplus.concert.api.waiting.WaitingResponse.CreatedResult;
import com.hhplus.concert.domain.user.UserRepository;
import com.hhplus.concert.domain.waiting.WaitingException.WaitingError;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRepository waitingRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreatedResult createOrGetToken(long userId) {
        boolean validUser = userRepository.existsByUserId(userId);
        if (!validUser) {
            throw new WaitingException(WaitingError.USER_NOT_FOUND);
        }

        WaitingEntity waiting = waitingRepository.findNotExpiredByUserId(userId);

        if (waiting == null) {
            waiting = new WaitingEntity(userId);
            waitingRepository.insertOne(waiting);
        }

        return CreatedResult.from(waiting);
    }

    @Transactional
    public CheckedResult checkTokenStatus(String token) {
        WaitingEntity waiting = waitingRepository.findOneByToken(token);
        if (waiting == null) {
            throw new WaitingException(WaitingError.TOKEN_NOT_FOUND);
        }
        waiting.checkExpired();

        if (waiting.isActive()) {
            return CheckedResult.activeFrom(waiting);
        }

        int nowActive = waitingRepository.countAllStatusActive();
        List<WaitingEntity> waitings = waitingRepository.findAllStatusWaiting();

        int waitingNo = waitings.indexOf(waiting) + 1;

        if (WaitingEntity.canActivate(nowActive, waitingNo)) {
            waiting.activate();
            return CheckedResult.activeFrom(waiting);
        }

        return CheckedResult.from(waiting, waitingNo);
    }
}
