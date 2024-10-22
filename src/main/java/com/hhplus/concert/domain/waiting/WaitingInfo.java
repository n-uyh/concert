package com.hhplus.concert.domain.waiting;

import java.time.LocalDateTime;

public class WaitingInfo {
    public record Created(
        String token,
        String status,
        LocalDateTime createdAt
    ) {

        public Created(WaitingEntity entity) {
            this(entity.getToken(), entity.status(), entity.getCreatedAt());
        }
    }

    public record TokenInfo(
        String token,
        String status,
        long waitingNo,
        LocalDateTime updatedAt
    ) {

        public TokenInfo(WaitingEntity entity, long waitingNo) {
            this(entity.getToken(), entity.status(), waitingNo, entity.getUpdatedAt());
        }
    }
}
