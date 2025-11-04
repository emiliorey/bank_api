package com.bank.bank_api.infraestructure.handler.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FeignErrorResponse extends ErrorResponse {

    private final String method;
    private final String url;

    public FeignErrorResponse(int status, String message, String method, String url) {
        super(HttpStatus.valueOf(status), message);
        this.method = method;
        this.url = url;
    }
}
