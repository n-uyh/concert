package com.hhplus.concert.infra.waiting;

import com.hhplus.concert.domain.waiting.WaitingEntity;
import com.hhplus.concert.domain.waiting.WaitingStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingJpaRepository extends JpaRepository<WaitingEntity, Long> {

    WaitingEntity findTopByUserIdAndStatusIsNot(long userId, WaitingStatus status);

    WaitingEntity findOneByToken(String token);

    int countByStatusOrderByIdDesc(WaitingStatus status);

    List<WaitingEntity> findAllByStatusOrderByIdAsc(WaitingStatus status);

}
