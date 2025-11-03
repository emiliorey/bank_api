package com.bank.bank_api.infraestructure.handler.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorResponse extends ErrorResponse {

    private final List<FieldErrorDetail> fieldErrors;

    public ValidationErrorResponse(HttpStatus status, String message) {
        super(status, message);
        this.fieldErrors = new ArrayList<>();
    }

    public void addFieldError(String field, String message) {
        fieldErrors.add(new FieldErrorDetail(field, message));
    }
}
