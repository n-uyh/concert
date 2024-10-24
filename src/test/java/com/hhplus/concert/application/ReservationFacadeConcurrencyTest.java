package com.hhplus.concert.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.hhplus.concert.domain.concert.ConcertException;
import com.hhplus.concert.domain.concert.ConcertException.ConcertError;
import com.hhplus.concert.domain.concert.ConcertSeatEntity;
import com.hhplus.concert.domain.reservation.ReservationCommand;
import com.hhplus.concert.infra.concert.ConcertSeatsJpaRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("좌석예약 동시성 통합테스트")
class ReservationFacadeConcurrencyTest {

    @Autowired
    ReservationFacade reservationFacade;

    @Autowired
    ConcertSeatsJpaRepository concertSeatsJpaRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        ConcertSeatEntity seat = new ConcertSeatEntity(1, 1, 1, 100_000, false);
        concertSeatsJpaRepository.save(seat);
    }

    @Test
    @DisplayName("동시에 여러명이 하나의 좌석을 예약하려고 하는 경우, 한명만 성공한다.")
    void reservationConcurrencyTest() throws InterruptedException {
        long seatId = 1;
        int count = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        CountDownLatch latch = new CountDownLatch(count);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);
        for (int i = 0; i < count; i++) {
            long userId = i + 1;
            executorService.submit(() -> {
                try {
                    reservationFacade.reserveSeat(new ReservationCommand.ReserveSeat(seatId, userId));
                    successCount.getAndAdd(1);
                } catch (ConcertException e) {
                    if (e.getErrorCode() == ConcertError.SEAT_ALREADY_OCCUPIED) {
                        errorCount.getAndAdd(1);
                    }
                } catch (Exception e) {
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();
        assertEquals(1, successCount.intValue());
        assertEquals(count-1, errorCount.intValue());
    }

}
