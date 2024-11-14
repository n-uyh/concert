package com.hhplus.concert.infra.event;

import com.hhplus.concert.domain.payment.PaymentEvent;
import com.hhplus.concert.domain.payment.PaymentEventPublisher;
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
