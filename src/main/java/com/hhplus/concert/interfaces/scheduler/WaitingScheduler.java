package com.hhplus.concert.interfaces.scheduler;

import com.hhplus.concert.domain.waiting.WaitingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WaitingScheduler {

    private final WaitingService waitingService;

    @Scheduled(fixedDelay = 3000, initialDelay = 2000)
    public void activate() {
        log.info("start waiting scheduler - activate");
        waitingService.activate();
    }

}
