package com.hhplus.concert.api.waiting;

import com.hhplus.concert.api.waiting.WaitingResponse.CheckedResult;
import com.hhplus.concert.api.waiting.WaitingResponse.CreatedResult;
import com.hhplus.concert.domain.waiting.WaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<CreatedResult> requestToken(
        @RequestHeader("Hh-User-Id") long userId
    ) {
        CreatedResult response = waitingService.createOrGetToken(userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<CheckedResult> checkTokenStatus(
        @RequestHeader("Hh-Waiting-Token") String token
    ) {
        CheckedResult response = waitingService.checkTokenStatus(token);
        return ResponseEntity.ok(response);
    }

}
