package com.hhplus.concert.infra.user;

import com.hhplus.concert.domain.user.PointHistoryEntity;
import com.hhplus.concert.domain.user.UserEntity;
import com.hhplus.concert.domain.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    public Optional<UserEntity> findOneUser(long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public void insertPointHistory(PointHistoryEntity entity) {
        pointHistoryJpaRepository.save(entity);
    }
}
