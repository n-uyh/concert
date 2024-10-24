package com.hhplus.concert.infra.point;

import com.hhplus.concert.domain.point.PointEntity;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface PointJpaRepository extends JpaRepository<PointEntity, Long> {

    Optional<PointEntity> findOneByUserId(long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    PointEntity findOneWithLockByUserId(long userId);
}
