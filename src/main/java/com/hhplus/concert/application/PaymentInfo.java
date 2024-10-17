package com.hhplus.concert.application;

import com.hhplus.concert.application.ReservationInfo.ReservedInfo;
import com.hhplus.concert.domain.payment.PaymentEntity;
import java.time.LocalDateTime;

public class PaymentInfo {

    public record CommonPayInfo(
        long payId,
        String payStatus,
        LocalDateTime payedAt
    ) {
       public static CommonPayInfo from(PaymentEntity entity) {
           return new CommonPayInfo(entity.getId(), entity.getStatus().name(), entity.getCreatedAt());
       }
    }

    public record PayedInfo(
        ReservedInfo reservation,
        CommonPayInfo payment
    ) {
    }

}
