package com.hhplus.concert.infra.user;

import com.hhplus.concert.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public boolean existsByUserId(long userId) {
        return jpaRepository.existsById(userId);
    }
}
