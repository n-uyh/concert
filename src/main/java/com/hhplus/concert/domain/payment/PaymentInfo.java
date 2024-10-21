package com.hhplus.concert.domain.payment;

import com.hhplus.concert.domain.reservation.ReservationInfo.ReservedInfo;
import java.time.LocalDateTime;

public class PaymentInfo {

    public record CommonPayInfo(
        long payId,
        String payStatus,
        LocalDateTime payedAt
    ) {
       public static CommonPayInfo of(PaymentEntity entity) {
           return new CommonPayInfo(entity.getId(), entity.getStatus().name(), entity.getCreatedAt());
       }
    }

    public record PayedInfo(
        ReservedInfo reservation,
        CommonPayInfo payment
    ) {
    }

}
