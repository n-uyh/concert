package com.hhplus.concert.domain.point;

public class PointInfo {
    public record Common(
        long userId,
        long point
    ) {

        public static Common of(PointEntity entity) {
            return new Common(entity.getUserId(), entity.getPoint());
        }
    }

}
