package com.hhplus.concert.interfaces.scheduler;

import com.hhplus.concert.domain.payment.PaymentOutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventScheduler {

    private final PaymentOutboxService paymentOutboxService;


    @Scheduled(fixedDelay = 1000 * 60, initialDelay = 2000)
    public void republishPayCompletedEvents() {
        log.info("start payment event scheduler - republishPayCompletedEvents");
        paymentOutboxService.republishPayCompletedEvents();
    }

    @Scheduled(cron = "0 30 2 * * *", zone = "Asia/Seoul")
    public void emptyPublished() {
        log.info("start payment event scheduler - emptyPublished");
        paymentOutboxService.emptyPublished();
    }


}
