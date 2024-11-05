package com.hhplus.concert.domain.support.lock;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

@Slf4j
public class AutoCloseableRLock implements AutoCloseable{

    private final RLock rLock;
    @Getter
    private final boolean locked;

    public AutoCloseableRLock(RLock rLock, boolean isLocked) {
        this.rLock = rLock;
        this.locked = isLocked;
    }

    @Override
    public void close() {
        if (locked && rLock.isHeldByCurrentThread()) {
            try {
                rLock.unlock();
            } catch (IllegalMonitorStateException e) {
                log.info("Redisson Lock Already Unlock");
            }
        }
    }
}
