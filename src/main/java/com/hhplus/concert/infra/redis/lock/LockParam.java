package com.hhplus.concert.infra.redis.lock;

import com.hhplus.concert.domain.support.lock.DistributedLock;
import java.util.concurrent.TimeUnit;

public record LockParam (
    String key,
    long waitTime,
    TimeUnit timeUnit
) {

    public static LockParam of(DistributedLock distributedLock, String key) {
        return new LockParam(key, distributedLock.waitTime(), distributedLock.timeUnit());
    }

}
