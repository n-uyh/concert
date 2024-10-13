package com.hhplus.concert.api.waiting;

import com.hhplus.concert.domain.waiting.WaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/waiting")
public class WaitingController implements IWaitingController {

    private final WaitingService waitingService;

    @PostMapping
    public ResponseEntity<WaitingResponse> requestToken(
        @RequestHeader("Hh-User-Id") long userId
    ) {
        WaitingResponse response = waitingService.createOrGetToken(userId);
        return ResponseEntity.ok(response);
    }

}
