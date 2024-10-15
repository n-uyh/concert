package com.hhplus.concert.api.concert;

import com.hhplus.concert.api.concert.ConcertRequest.Available;
import com.hhplus.concert.api.concert.ConcertResponse.ConcertList;
import com.hhplus.concert.application.ConcertFacade;
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
@RequestMapping("/concerts")
public class ConcertController implements IConcertController {

    private final ConcertFacade concertFacade;

    @GetMapping
    public ResponseEntity<ConcertList> availableConcerts(
        @RequestHeader("Hh-Waiting-Token") String token,
        @ModelAttribute Available request
    ) {
        ConcertList concerts = ConcertList.from(concertFacade.findAllAvailable(request.toCommand(token)));
        return ResponseEntity.ok(concerts);
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
