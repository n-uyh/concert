package com.hhplus.concert.api.waiting;

import com.hhplus.concert.api.waiting.WaitingResponse.CreatedResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Waiting", description = "대기열 API")
public interface IWaitingController {

    @Operation(summary = "사용자 토큰 발급/조회 기능", description = "사용자의 대기열토큰을 발급 및 조회합니다.")
    @ApiResponse(responseCode = "200", description = "토큰반환")
    ResponseEntity<CreatedResult> requestToken(
        @Schema(description = "사용자id") long userId
    );

}
