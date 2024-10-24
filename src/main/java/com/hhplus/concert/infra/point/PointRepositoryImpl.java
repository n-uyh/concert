package com.hhplus.concert.infra.point;

import com.hhplus.concert.domain.point.PointEntity;
import com.hhplus.concert.domain.point.PointHistoryEntity;
import com.hhplus.concert.domain.point.PointRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;
    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    @Override
    public Optional<PointEntity> findUserPoint(long userId) {
        return pointJpaRepository.findOneByUserId(userId);
    }

    @Override
    public void insertPointHistory(PointHistoryEntity entity) {
        pointHistoryJpaRepository.save(entity);
    }
}
