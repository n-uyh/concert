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
    public ResponseEntity<CreatedResult> requestToken(
    ) {
        CreatedResult response = CreatedResult.from(waitingService.createToken());
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<TokenResult> getTokenWithWaitingNo(
        @RequestHeader("Hh-Waiting-Token") String token
    ) {
        TokenResult response = TokenResult.from(waitingService.getTokenWithWaitingNo(token));
        return ResponseEntity.ok(response);
    }

}
