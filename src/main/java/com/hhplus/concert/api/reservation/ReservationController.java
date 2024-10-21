package com.hhplus.concert.api.reservation;

import com.hhplus.concert.api.reservation.ReservationResponse.Reserved;
import com.hhplus.concert.application.ReservationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController implements IReservationController {

    private final ReservationFacade reservationFacade;

    @PostMapping
    public ResponseEntity<ReservationResponse.Reserved> createReservation(
        @RequestHeader("Hh-Waiting-Token") String token,
        @RequestBody ReservationRequest.Seat request
    ) {
        Reserved reservation = Reserved.of(reservationFacade.reserveSeat(request.toCommand(token)));
        return ResponseEntity.ok(reservation);
    }

}
