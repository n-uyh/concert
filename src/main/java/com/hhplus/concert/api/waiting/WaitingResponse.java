package com.hhplus.concert.api.waiting;

import com.hhplus.concert.domain.waiting.WaitingEntity;
import java.time.LocalDateTime;

public record WaitingResponse(
    long userId,
    String token,
    String status,
    LocalDateTime createdAt
) {

    public static WaitingResponse from(WaitingEntity entity) {
        return new WaitingResponse(entity.getUserId(), entity.getToken(), entity.status(), entity.getCreatedAt());
    }

}
