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

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController implements IReservationController {

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
        @RequestHeader("X-Waiting-Header") String token,
        @RequestBody ReservationRequest request
    ) {
        ReservationResponse mock = new ReservationResponse(1,1,100_000,"결제대기", LocalDateTime.now());
        return ResponseEntity.ok(mock);
    }

    @PatchMapping
    public ResponseEntity<ReservationResponse> payToReservation(
        @RequestHeader("X-Waiting-Header") String token,
        @RequestBody ReservationPayReqeust reqeust
    ) {
        ReservationResponse mock = new ReservationResponse(1,1,100_000,"결제완료", LocalDateTime.now());
        return ResponseEntity.ok(mock);
    }

}
