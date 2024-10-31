package com.hhplus.concert.domain.point;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.hhplus.concert.domain.point.PointException.PointError;
import com.hhplus.concert.infra.db.point.PointJpaRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("포인트 동시성 통합테스트")
class PointServiceConcurrencyTest {

    @Autowired
    PointService pointService;

    @Autowired
    PointJpaRepository pointJpaRepository;

    @BeforeEach
    void setUp() {
        pointJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("포인트 충전을 10_000원씩 10번 충전하면 10_000원만 충전되고, 9번은 낙관적락 에러가 발생한다.")
    void whenChargeConcurrentlyButAllSuccess() throws InterruptedException {
        long userId = 1L;
        long chargeAmount = 10_000;

        int count = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        CountDownLatch latch = new CountDownLatch(count);

        pointJpaRepository.save(new PointEntity(1, userId, 0));

        AtomicInteger errorCount = new AtomicInteger(0);
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    pointService.chargePoint(new PointCommand.Charge(userId, chargeAmount));
                } catch (OptimisticLockingFailureException e) {
                    errorCount.getAndAdd(1);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();
        PointEntity point = pointJpaRepository.findOneByUserId(userId).get();
        assertEquals(10_000, point.getPoint());
        assertEquals(9,errorCount.intValue());
    }

    @Test
    @DisplayName("현재 포인트 잔액이 80_000원인 사용자가 동시에 50_000원씩 10번 사용을 시도하면 총 50_000원만 차감되어 잔액은 30_000원이다.(INSUFFICIENT_POINT_ERROR 9번 발생)")
    void test() throws InterruptedException {
        long userId = 1;
        pointJpaRepository.save(new PointEntity(1, userId, 80_000));

        int count = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        CountDownLatch latch = new CountDownLatch(count);

        long price = 50_000;
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    pointService.pay(new PointCommand.Pay(userId, price));
                    successCount.getAndAdd(1);
                } catch (PointException e) {
                    if (e.getErrorCode() == PointError.INSUFFICIENT_POINT_ERROR) {
                        errorCount.getAndAdd(1);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();
        PointEntity point = pointJpaRepository.findOneByUserId(userId).get();
        assertEquals(30_000,point.getPoint());
        assertEquals(1, successCount.intValue());
        assertEquals(9, errorCount.intValue());
    }

}
