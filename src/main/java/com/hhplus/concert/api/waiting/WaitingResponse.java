package com.hhplus.concert.api.waiting;

import java.time.LocalDateTime;

public record WaitingResponse(
    long userId,
    String token,
    String status,
    int waitingNo,
    LocalDateTime createdAt
) {

}
