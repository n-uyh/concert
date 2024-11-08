package com.hhplus.concert.domain.support.lock;

import com.hhplus.concert.infra.redis.lock.LockParam;

public interface LockRepository {

    AutoCloseableRLock lock(LockParam param) throws InterruptedException;
}
