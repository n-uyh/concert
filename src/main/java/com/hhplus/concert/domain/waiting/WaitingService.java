package com.hhplus.concert.domain.waiting;

import com.hhplus.concert.api.waiting.WaitingResponse;
import com.hhplus.concert.domain.waiting.WaitingInfo.CreatedInfo;
import java.time.LocalDateTime;
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

}
