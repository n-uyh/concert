package com.hhplus.concert.domain.point;

import java.util.Optional;

public interface PointRepository {
    Optional<PointEntity> findUserPoint(long userId);
    void insertPointHistory(PointHistoryEntity entity);
    PointEntity findUserPointWithLock(long userId);
}
