package com.hhplus.concert.infra.redis;

import com.hhplus.concert.support.AopForTransaction;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DistributedLockAop {

    private final RedissonRepository redissonRepository;
    private final AopForTransaction aopForTransaction;

    @Around("@annotation(com.hhplus.concert.infra.redis.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        try (AutoCloseableRLock lock = redissonRepository.lock(distributedLock)) {
            if (!lock.isLocked()) throw new AlreadyLockedException("key already locked");
            return aopForTransaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            log.warn("redisson interrupted : {} - {}", method.getName(), distributedLock.key());
            throw e;
        }
    }

}
