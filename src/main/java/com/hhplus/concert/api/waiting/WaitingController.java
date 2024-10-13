package com.hhplus.concert.api.waiting;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/waiting")
public class WaitingController implements IWaitingController {

    @PostMapping
    public ResponseEntity<WaitingResponse> requestToken(
        long userId
    ) {
        WaitingResponse mock =
            new WaitingResponse(1, UUID.randomUUID().toString(), "대기", 20, LocalDateTime.now());
        return ResponseEntity.ok(mock);
    }

}
