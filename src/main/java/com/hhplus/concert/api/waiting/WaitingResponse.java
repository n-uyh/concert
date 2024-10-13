package com.hhplus.concert.api.waiting;

import com.hhplus.concert.domain.waiting.WaitingEntity;
import java.time.LocalDateTime;

public class WaitingResponse {
    public record CreatedResult(
        long userId,
        String token,
        String status,
        LocalDateTime createdAt
    ) {
        public static CreatedResult from(WaitingEntity entity) {
            return new CreatedResult(entity.getUserId(), entity.getToken(), entity.status(), entity.getCreatedAt());
        }
    }

    public record CheckedResult(
        long userId,
        String token,
        String status,
        long waitingNo,
        LocalDateTime updatedAt
    ) {

        public static CheckedResult from(WaitingEntity entity, long waitingNo) {
            return new CheckedResult(entity.getUserId(), entity.getToken(), entity.status(), waitingNo, entity.getUpdatedAt());
        }

        public static CheckedResult activeFrom(WaitingEntity entity) {
            return CheckedResult.from(entity, 0);
        }
    }
}
