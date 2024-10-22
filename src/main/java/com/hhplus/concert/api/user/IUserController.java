package com.hhplus.concert.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User", description = "사용자 API")
public interface IUserController {

    @Operation(summary = "사용자 포인트 잔액 충전", description = "사용자의 포인트 잔액을 충전합니다.")
    @ApiResponse(responseCode = "200", description = "충전성공")
    ResponseEntity<UserResponse.PointResult> chargeUserPoint(
        UserRequest.ChargePoint request
    );

    @Operation(summary = "사용자 포인트 잔액 조회", description = "사용자의 포인트 잔액을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회성공")
    ResponseEntity<UserResponse.PointResult> point(
        long userId
    );

}
