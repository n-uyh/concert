package com.hhplus.concert.api.concert;

import com.hhplus.concert.api.concert.ConcertRequest.Available;
import com.hhplus.concert.api.concert.ConcertResponse.ConcertList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;

@Tag(name = "Concert", description = "콘서트 API")
public interface IConcertController {

    @Operation(summary = "예약가능 날짜 조회", description = "예약가능한 공연날짜 리스트를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회성공")
    ResponseEntity<ConcertList> availableConcerts(
        @Schema(description = "토큰") String token,
        @ParameterObject Available request
    );

    @Operation(summary = "예약가능 좌석 조회", description = "공연정보로 공연좌석 리스트를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회성공")
    ResponseEntity<List<SeatResponse>> availableSeats(
        @Schema(description = "토큰") String token,
        @Schema(description = "공연id") long concertId
    );

}
