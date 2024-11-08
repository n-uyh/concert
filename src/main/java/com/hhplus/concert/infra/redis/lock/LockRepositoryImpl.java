package com.hhplus.concert.infra.redis.lock;

import com.hhplus.concert.domain.support.lock.AutoCloseableRLock;
import com.hhplus.concert.domain.support.lock.LockRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LockRepositoryImpl implements LockRepository {

    private final RedissonClient redissonClient;

    public AutoCloseableRLock lock(LockParam param) throws InterruptedException {
        RLock rLock = redissonClient.getLock(param.key());
        boolean locked = rLock.tryLock(param.waitTime(), param.timeUnit());
        return new AutoCloseableRLock(rLock, locked);
    }
}
