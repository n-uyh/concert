package com.hhplus.concert.domain.support.lock;

public interface LockRepository {

    AutoCloseableRLock lock(final DistributedLock distributedLock) throws InterruptedException;
}
