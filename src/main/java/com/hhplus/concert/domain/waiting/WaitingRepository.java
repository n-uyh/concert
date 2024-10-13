package com.hhplus.concert.domain.waiting;

public interface WaitingRepository {

    void insertOne(WaitingEntity entity);

    WaitingEntity findNotExpiredByUserId(long userId);

}
