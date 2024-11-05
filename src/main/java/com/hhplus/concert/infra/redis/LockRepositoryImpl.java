package com.hhplus.concert.infra.redis;

import com.hhplus.concert.domain.support.lock.AutoCloseableRLock;
import com.hhplus.concert.domain.support.lock.DistributedLock;
import com.hhplus.concert.domain.support.lock.LockRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LockRepositoryImpl implements LockRepository {

    private final RedissonClient redissonClient;

    public AutoCloseableRLock lock(final DistributedLock distributedLock) throws InterruptedException {
        RLock rLock = redissonClient.getLock(distributedLock.key());
        boolean locked = rLock.tryLock(distributedLock.waitTime(), distributedLock.timeUnit());
        return new AutoCloseableRLock(rLock, locked);
    }
}
