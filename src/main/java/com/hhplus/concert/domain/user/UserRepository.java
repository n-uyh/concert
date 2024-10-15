package com.hhplus.concert.domain.user;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findOneUser(long userId);

    void insertPointHistory(PointHistoryEntity entity);

}
