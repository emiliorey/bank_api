package com.bank.bank_api.infraestructure.handler.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RateLimiterErrorResponse extends ErrorResponse {

    private final String rateLimiter;

    public RateLimiterErrorResponse(HttpStatus status, String message, String rateLimiter) {
        super(status, message);
        this.rateLimiter = rateLimiter;
    }
}
