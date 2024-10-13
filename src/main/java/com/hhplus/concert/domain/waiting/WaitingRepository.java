package com.hhplus.concert.domain.waiting;

import java.util.List;

public interface WaitingRepository {

    void insertOne(WaitingEntity entity);

    WaitingEntity findNotExpiredByUserId(long userId);

    WaitingEntity findOneByToken(String token);

    int countAllStatusActive();

    List<WaitingEntity> findAllStatusWaiting();
}
