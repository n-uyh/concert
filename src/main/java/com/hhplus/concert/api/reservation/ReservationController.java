package com.hhplus.concert.api.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Reservation", description = "좌석예약 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    @Operation(summary = "좌석 예약 요청", description = "한 좌석에 예약을 요청합니다.")
    @ApiResponse(responseCode = "200", description = "예약성공")
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
        @Schema(description = "토큰")
        @RequestHeader("X-Waiting-Header") String token,
        @RequestBody ReservationRequest request
    ) {
        ReservationResponse mock = new ReservationResponse(1,1,100_000,"결제대기", LocalDateTime.now());
        return ResponseEntity.ok(mock);
    }

    @Operation(summary = "예약내역 결제 요청", description = "예약내역에 관한 결제를 요청합니다.")
    @ApiResponse(responseCode = "200", description = "결제성공")
    @PatchMapping
    public ResponseEntity<ReservationResponse> payToReservation(
        @Schema(description = "토큰")
        @RequestHeader("X-Waiting-Header") String token,
        @RequestBody ReservationPayReqeust reqeust
    ) {
        ReservationResponse mock = new ReservationResponse(1,1,100_000,"결제완료", LocalDateTime.now());
        return ResponseEntity.ok(mock);
    }

}
