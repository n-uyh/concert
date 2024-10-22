package com.hhplus.concert.api.user;

import com.hhplus.concert.api.user.UserResponse.PointResult;
import com.hhplus.concert.domain.user.UserCommand.GetPoint;
import com.hhplus.concert.application.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController implements IUserController {

    private final UserFacade userFacade;

    @PatchMapping("charge")
    public ResponseEntity<UserResponse.PointResult> chargeUserPoint(
        @RequestBody UserRequest.ChargePoint request
    ) {
        PointResult result = PointResult.of(userFacade.charge(request.toCommand()));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{userId}/point")
    public ResponseEntity<UserResponse.PointResult> point(
        @PathVariable long userId
    ) {
        PointResult result = PointResult.of(userFacade.getPoint(new GetPoint(userId)));
        return ResponseEntity.ok(result);
    }

}
