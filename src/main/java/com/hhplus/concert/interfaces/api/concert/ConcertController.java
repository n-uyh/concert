package com.hhplus.concert.interfaces.api.concert;

import com.hhplus.concert.domain.concert.ConcertCommand;
import com.hhplus.concert.interfaces.api.concert.ConcertResponse.ConcertList;
import com.hhplus.concert.domain.concert.ConcertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
        @PathVariable long concertId
    ) {
        ConcertResponse.SeatList seats = ConcertResponse.SeatList.of(concertService.findAvailableConcertSeats(new ConcertCommand.Seat(concertId)));
        return ResponseEntity.ok(seats);
    }

}
