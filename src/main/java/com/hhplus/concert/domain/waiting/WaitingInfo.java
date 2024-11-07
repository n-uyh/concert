package com.hhplus.concert.domain.waiting;

import java.time.LocalDateTime;
import org.springframework.util.StringUtils;

public class WaitingInfo {
    public record Created(
        String token,
        String status,
        LocalDateTime createdAt
    ) {
        public static Created of(WaitingToken token) {
            return new Created(token.token(), token.status().name(), token.createdAt());
        }
    }

    public record TokenInfo(
        String token,
        String status,
        long waitingNo
    ) {

        public static TokenInfo waiting(String token, Long waitingNo) {
            if (waitingNo == null) {
                return null;
            }
            return new TokenInfo(token, WaitingStatus.WAIT.name(), waitingNo);
        }

        public static TokenInfo acitve(String token) {
            if (StringUtils.hasText(token)) {
                return new TokenInfo(token, WaitingStatus.ACTIVE.name(), 0);
            }
            return null;
        }
    }

    public record ActivateTarget(
        String token
    ) {

    }
}
