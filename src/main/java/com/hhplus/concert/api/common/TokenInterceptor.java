package com.hhplus.concert.api.common;

import com.hhplus.concert.domain.waiting.WaitingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final WaitingService waitingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        String token = request.getHeader("Hh-Waiting-Token");
        waitingService.checkTokenIsActive(token);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
