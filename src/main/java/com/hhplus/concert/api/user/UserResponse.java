package com.hhplus.concert.api.user;

import com.hhplus.concert.application.UserInfo;

public class UserResponse {
    public record PointResult(
        long id,
        long point
    ) {

        public static PointResult from(UserInfo.Point info) {
            return new PointResult(info.userId(), info.point());
        }

    }
}
