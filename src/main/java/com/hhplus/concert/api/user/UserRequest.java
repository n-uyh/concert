package com.hhplus.concert.api.user;

import com.hhplus.concert.domain.point.PointCommand.Charge;
import io.swagger.v3.oas.annotations.media.Schema;

public class UserRequest {
    public record ChargePoint(
        @Schema(description = "사용자 id")
        long userId,
        @Schema(description = "충전금액")
        long amount
    ) {

        public Charge toCommand() {
            return new Charge(userId, amount);
        }

    }

}
