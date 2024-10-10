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

@Tag(name = "User", description = "사용자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {

    @Operation(summary = "사용자 포인트 잔액 충전", description = "사용자의 포인트 잔액을 충전합니다.")
    @ApiResponse(responseCode = "200", description = "충전성공")
    @PatchMapping("charge")
    public ResponseEntity<UserPointResponse> chargeUserPoint(
        @Schema(description = "토큰")
        @RequestHeader("X-Waiting-Header") String token,
        @RequestBody PointChargeRequest request
    ) {
        UserPointResponse mock = new UserPointResponse(1, 200_000);
        return ResponseEntity.ok(mock);
    }

    @Operation(summary = "사용자 포인트 잔액 조회", description = "사용자의 포인트 잔액을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회성공")
    @GetMapping("point")
    public ResponseEntity<UserPointResponse> point(
        @Schema(description = "토큰")
        @RequestHeader("X-Waiting-Header") String token
    ) {
        UserPointResponse mock = new UserPointResponse(1, 200_000);
        return ResponseEntity.ok(mock);
    }

}
