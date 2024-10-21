package com.hhplus.concert.domain.user;

public class UserInfo {
    public record Point(
        long userId,
        long point
    ) {

        public static Point of(UserEntity entity) {
            return new UserInfo.Point(entity.getId(), entity.getPoint());
        }
    }

}
