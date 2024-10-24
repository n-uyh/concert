package com.hhplus.concert.api.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Reservation", description = "좌석예약 API")
public interface IReservationController {

    @Operation(summary = "좌석 예약 요청", description = "한 좌석에 예약을 요청합니다.")
    @ApiResponse(responseCode = "200", description = "예약성공")
    ResponseEntity<ReservationResponse.Reserved> createReservation(
        ReservationRequest.Seat request
    );
}
