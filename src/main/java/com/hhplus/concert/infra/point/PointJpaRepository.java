package com.hhplus.concert.infra.point;

import com.hhplus.concert.domain.point.PointEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointJpaRepository extends JpaRepository<PointEntity, Long> {

    Optional<PointEntity> findOneByUserId(long userId);
}
