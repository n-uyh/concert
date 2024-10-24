package com.hhplus.concert.api.waiting;

import com.hhplus.concert.api.waiting.WaitingResponse.TokenResult;
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
    public ResponseEntity<CreatedResult> issueToken(
    ) {
        CreatedResult response = CreatedResult.of(waitingService.issue());
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<TokenResult> getToken(
        @RequestHeader("Hh-Waiting-Token") String token
    ) {
        TokenResult response = TokenResult.of(waitingService.getToken(token));
        return ResponseEntity.ok(response);
    }

}
