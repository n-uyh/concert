package com.hhplus.concert.api.user;

import com.hhplus.concert.domain.user.UserInfo;

public class UserResponse {
    public record PointResult(
        long id,
        long point
    ) {

        public static PointResult of(UserInfo.Point info) {
            return new PointResult(info.userId(), info.point());
        }

    }
}
