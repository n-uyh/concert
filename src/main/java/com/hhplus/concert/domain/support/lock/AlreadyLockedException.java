package com.hhplus.concert.domain.support.lock;

public class AlreadyLockedException extends RuntimeException {

    public AlreadyLockedException(String message) {
        super(message);
    }
}
