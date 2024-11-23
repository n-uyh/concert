package com.hhplus.concert.infra.spring.event.payment;

import com.hhplus.concert.domain.payment.event.PaymentEvent;
import com.hhplus.concert.domain.payment.event.PaymentEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisherImpl implements PaymentEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publishPayCompletedEvent(PaymentEvent.PayCompleted event) {
        eventPublisher.publishEvent(event);
    }

}
