package com.hhplus.concert.api.concert;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Concert", description = "콘서트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertController {

    @Operation(summary = "예약가능 날짜 조회", description = "예약가능한 공연날짜 리스트를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회성공")
    @GetMapping
    public ResponseEntity<List<ConcertResponse>> availableConcerts(
        @Schema(description = "토큰")
        @RequestHeader("X-Waiting-Header") String token,
        @ParameterObject
        @ModelAttribute AvailableConcertRequest request
    ) {
        ConcertResponse mock = new ConcertResponse(1, "공연", LocalDate.now(), LocalTime.now());
        return ResponseEntity.ok(List.of(mock));
    }


    @Operation(summary = "예약가능 좌석 조회", description = "공연정보로 공연좌석 리스트를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회성공")
    @GetMapping("/{concertId}")
    public ResponseEntity<List<SeatResponse>> availableSeats(
        @Schema(description = "토큰")
        @RequestHeader("X-Waiting-Header") String token,
        @Schema(description = "공연id")
        @PathVariable long concertId
    ) {
        SeatResponse mock1 = new SeatResponse(1, 1, 100_000, false);
        SeatResponse mock2 = new SeatResponse(2, 2, 100_000, true);
        return ResponseEntity.ok(List.of(mock1, mock2));
    }

}
