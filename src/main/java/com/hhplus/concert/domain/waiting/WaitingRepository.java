package com.hhplus.concert.domain.waiting;

import java.util.List;
import java.util.Optional;

public interface WaitingRepository {

    void insertOne(WaitingEntity entity);

    Optional<WaitingEntity> findOneByToken(String token);

    List<WaitingEntity> findAllStatusWaiting();

    List<WaitingEntity> findActivateTargets(WaitingStatus status, int activatePersonnel);
}
