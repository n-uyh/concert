package com.hhplus.concert.infra.waiting;

import com.hhplus.concert.domain.waiting.WaitingEntity;
import com.hhplus.concert.domain.waiting.WaitingRepository;
import com.hhplus.concert.domain.waiting.WaitingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WaitingRepositoryImpl implements WaitingRepository {

    private final WaitingJpaRepository jpaRepository;

    @Override
    public void insertOne(WaitingEntity entity) {
        jpaRepository.save(entity);
    }

    @Override
    public WaitingEntity findNotExpiredByUserId(long userId) {
        return jpaRepository.findTopByUserIdAndStatusIsNot(userId, WaitingStatus.EXPIRED);
    }

    @Override
    public WaitingEntity findOneByToken(String token) {
        return jpaRepository.findOneByToken(token);
    }

    @Override
    public int countAllStatusActive() {
        return jpaRepository.countByStatusOrderByIdDesc(WaitingStatus.ACTIVE);
    }

    @Override
    public List<WaitingEntity> findAllStatusWaiting() {
        return jpaRepository.findAllByStatusOrderByIdAsc(WaitingStatus.WAITING);
    }
}
