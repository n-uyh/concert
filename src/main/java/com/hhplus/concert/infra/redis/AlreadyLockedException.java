package com.hhplus.concert.infra.redis;

public class AlreadyLockedException extends RuntimeException {

    public AlreadyLockedException(String message) {
        super(message);
    }
}
