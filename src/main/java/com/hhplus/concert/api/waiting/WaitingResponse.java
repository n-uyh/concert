package com.hhplus.concert.api.waiting;

import com.hhplus.concert.domain.waiting.WaitingInfo;
import java.time.LocalDateTime;

public class WaitingResponse {
    public record CreatedResult(
        String token,
        String status,
        LocalDateTime createdAt
    ) {
        public static CreatedResult from(WaitingInfo.CreatedInfo info) {
            return new CreatedResult(info.token(), info.status(), info.createdAt());
        }
    }

}
