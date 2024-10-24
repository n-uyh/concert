package com.hhplus.concert.api.user;

import com.hhplus.concert.domain.point.PointInfo.Common;

public class UserResponse {
    public record PointResult(
        long id,
        long point
    ) {

        public static PointResult of(Common info) {
            return new PointResult(info.userId(), info.point());
        }

    }
}
