package com.hhplus.concert.infra.waiting;

import com.hhplus.concert.domain.waiting.WaitingEntity;
import com.hhplus.concert.domain.waiting.WaitingRepository;
import com.hhplus.concert.domain.waiting.WaitingStatus;
import java.util.List;
import java.util.Optional;
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
    public Optional<WaitingEntity> findOneByToken(String token) {
        return Optional.of(jpaRepository.findOneByToken(token));
    }

    @Override
    public List<WaitingEntity> findAllStatusWaiting() {
        return jpaRepository.findAllByStatusOrderByIdAsc(WaitingStatus.WAIT);
    }
}
