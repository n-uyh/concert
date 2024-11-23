package com.hhplus.concert.interfaces.scheduler;

import com.hhplus.concert.application.ReservationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationScheduler {

    private final ReservationFacade reservationFacade;

    @Scheduled(fixedDelay = 1000 * 60 * 3, initialDelay = 3000)
    public void expire() {
        log.info("start reservation scheduler - expire");
        reservationFacade.expire();
    }

}
