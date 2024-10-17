package com.hhplus.concert.application;

import com.hhplus.concert.domain.user.UserEntity;

public class UserInfo {
    public record Point(
        long userId,
        long point
    ) {

        public static Point from(UserEntity entity) {
            return new UserInfo.Point(entity.getId(), entity.getPoint());
        }
    }

}
