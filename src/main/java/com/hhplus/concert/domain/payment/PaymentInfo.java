package com.hhplus.concert.domain.payment;

import com.hhplus.concert.domain.reservation.ReservationInfo.ReservedInfo;
import java.time.LocalDateTime;

public class PaymentInfo {

    public record Common(
        long payId,
        long userId,
        long price,
        String payStatus,
        LocalDateTime payedAt
    ) {
       public static Common of(PaymentEntity entity) {
           return new Common(entity.getId(), entity.getUserId(), entity.getPrice(), entity.getStatus().name(), entity.getCreatedAt());
       }
    }

    public record PayedInfo(
        ReservedInfo reservation,
        Common payment
    ) {
    }

}
