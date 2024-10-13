package com.hhplus.concert.domain.waiting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WaitingStatus {

    WAITING("대기"),
    ACTIVE("활성"),
    EXPIRED("만료")
    ;

    private final String option;
}
