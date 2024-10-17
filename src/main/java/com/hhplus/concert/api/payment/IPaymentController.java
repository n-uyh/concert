package com.hhplus.concert.api.payment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Payment", description = "결제 API")
public interface IPaymentController {

    @Operation(summary = "예약내역 결제 요청", description = "예약내역에 관한 결제를 요청합니다.")
    @ApiResponse(responseCode = "200", description = "결제성공")
    ResponseEntity<PaymentResponse.Payed> payToReservation(
        @Schema(description = "토큰") String token,
        PaymentRequest.CreatePayment reqeust
    );

}
