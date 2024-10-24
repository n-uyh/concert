package com.hhplus.concert.infra.point;

import com.hhplus.concert.domain.point.PointHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryJpaRepository extends JpaRepository<PointHistoryEntity, Long> {

}
