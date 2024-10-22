package com.hhplus.concert.application;

import com.hhplus.concert.domain.user.UserCommand;
import com.hhplus.concert.domain.user.UserInfo;
import com.hhplus.concert.domain.user.UserService;
import com.hhplus.concert.domain.waiting.WaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public UserInfo.Point charge(UserCommand.ChargePoint command) {
        return userService.chargePoint(command.userId(), command.amount());
    }

    public UserInfo.Point getPoint(UserCommand.GetPoint command) {
        return userService.getPoint(command.userId());
    }

}
