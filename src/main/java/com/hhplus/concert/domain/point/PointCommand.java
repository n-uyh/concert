package com.hhplus.concert.domain.point;

public class PointCommand {

    public record Charge(
        long userId,
        long amount
    ) {

    }

    public record Get(
        long userId
    ) {

    }

    public record Pay(
        long userId,
        long price
    ) {

    }

}
