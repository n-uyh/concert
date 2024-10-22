package com.hhplus.concert.domain.user;

public class UserCommand {

    public record ChargePoint(
        long userId,
        long amount
    ) {

    }

    public record GetPoint(
        long userId
    ) {

    }

}
