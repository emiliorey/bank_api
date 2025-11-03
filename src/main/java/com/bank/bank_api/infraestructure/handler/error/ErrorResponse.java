package com.bank.bank_api.infraestructure.handler.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;

    public ErrorResponse(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
    }
}
