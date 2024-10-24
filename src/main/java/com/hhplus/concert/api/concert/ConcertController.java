package com.hhplus.concert.api.concert;

import com.hhplus.concert.api.concert.ConcertResponse.ConcertList;
import com.hhplus.concert.domain.concert.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
public class ConcertController implements IConcertController {

    private final ConcertService concertService;

    @GetMapping
    public ResponseEntity<ConcertResponse.ConcertList> availableConcerts(
        @ModelAttribute ConcertRequest.Available request
    ) {
        ConcertList concerts = ConcertList.of(concertService.findAvailable(request.toCommand()));
        return ResponseEntity.ok(concerts);
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertResponse.SeatList> availableSeats(
        @ModelAttribute ConcertRequest.Seat request
    ) {
        ConcertResponse.SeatList seats = ConcertResponse.SeatList.of(concertService.findAvailableConcertSeats(request.toCommand()));
        return ResponseEntity.ok(seats);
    }

}
