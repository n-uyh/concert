package com.hhplus.concert.domain.waiting;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public record WaitingToken (
    String token,
    WaitingStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static WaitingToken issue(LocalDateTime createdAt) {
        return new WaitingToken(UUID.randomUUID().toString(),WaitingStatus.WAIT,createdAt,createdAt);
    }

    public static final int ACTIVATE_PERSONNEL = 20;
    public static final long ACTIVE_MINUTE = 10;
    public static final TimeUnit ACTIVE_TIMEUNIT = TimeUnit.MINUTES;

}
