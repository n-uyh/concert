package com.hhplus.concert.domain.user;

public interface UserRepository {

    boolean existsByUserId(long userId);

}
