package com.hhplus.concert.application;

public class UserCommand {

    public record ChargePoint(
        String token,
        long userId,
        long amount
    ) {

    }

}
