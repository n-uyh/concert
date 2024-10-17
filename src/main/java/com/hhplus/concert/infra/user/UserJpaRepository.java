package com.hhplus.concert.infra.user;

import com.hhplus.concert.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
