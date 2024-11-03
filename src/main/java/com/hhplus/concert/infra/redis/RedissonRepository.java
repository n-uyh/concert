package com.hhplus.concert.infra.redis;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedissonRepository {

    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;

    public AutoCloseableRLock lock(final DistributedLock distributedLock) throws InterruptedException {
        RLock rLock = redissonClient.getLock(REDISSON_LOCK_PREFIX + distributedLock.key());
        boolean locked = rLock.tryLock(distributedLock.waitTime(), distributedLock.timeUnit());
        return new AutoCloseableRLock(rLock, locked);
    }
}
