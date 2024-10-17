package com.hhplus.concert.domain.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.hhplus.concert.application.PaymentInfo.CommonPayInfo;
import com.hhplus.concert.application.ReservationInfo.ReservedInfo;
import com.hhplus.concert.domain.payment.PaymentException.PayError;
import com.hhplus.concert.domain.reservation.ReservationStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("결제서비스 단위테스트")
@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentService paymentService;

    @Mock
    PaymentRepository paymentRepository;


    @Test
    @DisplayName("예약정보가 RESERVED가 아닌 경우 CANNOT_PAY 에러가 발생한다.")
    void checkInvalidPay() {
        ReservationStatus status = ReservationStatus.CANCELED;
        LocalDateTime datetime = LocalDateTime.of(2024, 10, 10, 14, 0, 0);
        ReservedInfo target = new ReservedInfo(1, 1, 1, 100_000, status.name(), datetime,
            datetime.plusMinutes(5));

        PaymentException exception = assertThrows(PaymentException.class,
            () -> paymentService.createPayment(target));

        assertEquals(PayError.CANNOT_PAY, exception.getErrorCode());
    }

    @Test
    @DisplayName("결제 생성 성공케이스")
    void createPaymentSuccess() {
        ReservationStatus status = ReservationStatus.RESERVED;
        LocalDateTime datetime = LocalDateTime.of(2024, 10, 10, 14, 0, 0);
        ReservedInfo target = new ReservedInfo(1, 1, 1, 100_000, status.name(), datetime,
            datetime.plusMinutes(5));

        CommonPayInfo payment = paymentService.createPayment(target);

        verify(paymentRepository, times(1)).insertOne(any(PaymentEntity.class));
        verify(paymentRepository, times(1)).insertHistory(any(PaymentHistoryEntity.class));
        assertEquals(PaymentStatus.COMPLETED.name(),payment.payStatus());
    }

}
