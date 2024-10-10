package com.hhplus.concert.api.waiting;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Waiting", description = "대기열 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/waiting")
public class WaitingController {

    @Operation(summary = "사용자 토큰 발급/조회 기능", description = "사용자의 대기열토큰을 발급 및 조회합니다.")
    @ApiResponse(responseCode = "200", description = "토큰반환")
    @PostMapping
    public ResponseEntity<WaitingResponse> requestToken(
        @Schema(description = "사용자id")
        long userId
    ) {
        WaitingResponse mock =
            new WaitingResponse(1, UUID.randomUUID().toString(), "대기", 20, LocalDateTime.now());
        return ResponseEntity.ok(mock);
    }

}
