package com.hhplus.concert.api.concert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertController implements IConcertController {

    @GetMapping
    public ResponseEntity<List<ConcertResponse>> availableConcerts(
        @RequestHeader("Hh-Waiting-Token") String token,
        @ModelAttribute AvailableConcertRequest request
    ) {
        ConcertResponse mock = new ConcertResponse(1, "공연", LocalDate.now(), LocalTime.now());
        return ResponseEntity.ok(List.of(mock));
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<List<SeatResponse>> availableSeats(
        @RequestHeader("Hh-Waiting-Token") String token,
        @PathVariable long concertId
    ) {
        SeatResponse mock1 = new SeatResponse(1, 1, 100_000, false);
        SeatResponse mock2 = new SeatResponse(2, 2, 100_000, true);
        return ResponseEntity.ok(List.of(mock1, mock2));
    }

}
