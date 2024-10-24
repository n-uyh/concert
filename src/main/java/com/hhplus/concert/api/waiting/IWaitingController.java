package com.hhplus.concert.api.waiting;

import com.hhplus.concert.api.waiting.WaitingResponse.CreatedResult;
import com.hhplus.concert.api.waiting.WaitingResponse.TokenResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Waiting", description = "대기열 API")
public interface IWaitingController {

    @Operation(summary = "사용자 토큰 발급 기능", description = "사용자의 대기열토큰을 발급합니다.")
    @ApiResponse(responseCode = "200", description = "토큰반환")
    ResponseEntity<CreatedResult> issueToken();

    @Operation(summary = "사용자 토큰 조회 기능", description = "사용자의 대기열토큰을 상태 및 순번을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "토큰반환")
    ResponseEntity<TokenResult> getToken(
        @RequestHeader("Hh-Waiting-Token") String token
    );

}
