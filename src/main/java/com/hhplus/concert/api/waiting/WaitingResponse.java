package com.hhplus.concert.api.waiting;

import com.hhplus.concert.domain.waiting.WaitingInfo;
import com.hhplus.concert.domain.waiting.WaitingInfo.TokenInfo;
import java.time.LocalDateTime;

public class WaitingResponse {
    public record CreatedResult(
        String token,
        String status,
        LocalDateTime createdAt
    ) {
        public static CreatedResult of(WaitingInfo.Created info) {
            return new CreatedResult(info.token(), info.status(), info.createdAt());
        }
    }

    public record TokenResult(
        String token,
        String status,
        long waitingNo,
        LocalDateTime updatedAt
    ) {

        public static TokenResult of(TokenInfo info) {
            return new TokenResult(info.token(), info.status(), info.waitingNo(), info.updatedAt());
        }
    }

}
