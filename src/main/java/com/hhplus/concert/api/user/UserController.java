package com.hhplus.concert.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController implements IUserController {

    @PatchMapping("charge")
    public ResponseEntity<UserPointResponse> chargeUserPoint(
        @RequestHeader("X-Waiting-Header") String token,
        @RequestBody PointChargeRequest request
    ) {
        UserPointResponse mock = new UserPointResponse(1, 200_000);
        return ResponseEntity.ok(mock);
    }

    @GetMapping("point")
    public ResponseEntity<UserPointResponse> point(
        @RequestHeader("X-Waiting-Header") String token
    ) {
        UserPointResponse mock = new UserPointResponse(1, 200_000);
        return ResponseEntity.ok(mock);
    }

}
