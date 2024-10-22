package com.hhplus.concert.infra.waiting;

import com.hhplus.concert.domain.waiting.WaitingEntity;
import com.hhplus.concert.domain.waiting.WaitingStatus;
import java.util.List;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingJpaRepository extends JpaRepository<WaitingEntity, Long> {

    WaitingEntity findOneByToken(String token);

    List<WaitingEntity> findAllByStatusOrderByIdAsc(WaitingStatus status);

    List<WaitingEntity> findWithLimitByStatusOrderByIdAsc(WaitingStatus status, Limit limit);
}
