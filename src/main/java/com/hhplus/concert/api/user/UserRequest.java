package com.hhplus.concert.api.user;

import com.hhplus.concert.application.UserCommand;
import io.swagger.v3.oas.annotations.media.Schema;

public class UserRequest {
    public record ChargePoint(
        @Schema(description = "사용자 id")
        long userId,
        @Schema(description = "충전금액")
        long amount
    ) {

        public UserCommand.ChargePoint toCommand(String token) {
            return new UserCommand.ChargePoint(token, userId, amount);
        }

    }

}
