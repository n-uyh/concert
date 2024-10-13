package com.hhplus.concert.domain.waiting;

import com.hhplus.concert.api.waiting.WaitingResponse.CreatedResult;
import com.hhplus.concert.domain.user.UserRepository;
import com.hhplus.concert.domain.waiting.WaitingException.WaitingError;
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

}
