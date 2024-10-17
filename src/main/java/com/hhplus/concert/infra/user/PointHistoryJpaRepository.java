package com.hhplus.concert.infra.user;

import com.hhplus.concert.domain.user.PointHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryJpaRepository extends JpaRepository<PointHistoryEntity, Long> {

}
