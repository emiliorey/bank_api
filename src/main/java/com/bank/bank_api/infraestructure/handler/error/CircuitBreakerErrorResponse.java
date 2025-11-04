package com.bank.bank_api.infraestructure.handler.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CircuitBreakerErrorResponse extends ErrorResponse {

    private final String circuitBreaker;

    public CircuitBreakerErrorResponse(HttpStatus status, String message, String circuitBreaker) {
        super(status, message);
        this.circuitBreaker = circuitBreaker;
    }
}
