package com.hhplus.concert.api.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record PointChargeRequest(
    @Schema(description = "충전금액")
    long amount
) {

}
