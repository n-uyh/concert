package com.hhplus.concert.domain.user;

public class UserCommand {

    public record ChargePoint(
        String token,
        long userId,
        long amount
    ) {

    }

    public record GetPoint(
        String token,
        long userId
    ) {

    }

}
