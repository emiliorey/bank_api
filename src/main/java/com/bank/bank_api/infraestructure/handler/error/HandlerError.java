package com.bank.bank_api.infraestructure.handler.error;

import com.bank.bank_api.domain.exceptions.DuplicateBankException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TransactionRequiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class HandlerError {

    private static final String MESSAGE_ERROR = "Validation error in the submitted data";

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> responseStatusException(ResponseStatusException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ErrorResponse> handleConnectException(ConnectException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "Connection refused: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handleResourceAccessException(ResourceAccessException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "Error accessing remote resource: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpServerErrorException(HttpServerErrorException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.valueOf(ex.getStatusCode().value()), "Remote server error: " + ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.valueOf(ex.getStatusCode().value()), "Client error: " + ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<ErrorResponse> handleMismatchedInputException(MismatchedInputException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid JSON input: " + ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid data format: " + ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, "Entity not found: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT, "Data integrity violation: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, "No data found: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TransactionRequiredException.class)
    public ResponseEntity<ErrorResponse> handleTransactionRequiredException(TransactionRequiredException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction required: " + ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "HTTP message not readable: " + ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ErrorResponse> restClientError(RestClientException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedOperationException(UnsupportedOperationException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, "Operación no soportada: " + ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DuplicateBankException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateBankException(DuplicateBankException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT, "Bank exist: " + ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(HttpStatus.BAD_REQUEST, MESSAGE_ERROR);

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorResponse.addFieldError(fieldName, message);
        });

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<CircuitBreakerErrorResponse> handleCallNotPermitted(CallNotPermittedException ex) {
        log.warn("CircuitBreaker abierto: {}", ex.getMessage());
        String message = "The service is temporarily unavailable. Please try again later.";
        CircuitBreakerErrorResponse circuitBreakerErrorResponse =
                new CircuitBreakerErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, message, ex.getCausingCircuitBreakerName());
        return ResponseEntity
                .status(circuitBreakerErrorResponse.getStatus())
                .body(circuitBreakerErrorResponse);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<FeignErrorResponse> handleFeignNotFoundException(FeignException.NotFound ex) {
        String internalMessage = FeignErrorMessageHelper.extractErrorMessage(ex);
        FeignErrorResponse errorResponse = new FeignErrorResponse(
                ex.status(),
                internalMessage,
                ex.request().httpMethod().name(),
                ex.request().url()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<FeignErrorResponse> handleFeignBadRequest(FeignException.BadRequest ex) {
        String internalMessage = FeignErrorMessageHelper.extractErrorMessage(ex);
        FeignErrorResponse errorResponse = new FeignErrorResponse(
                ex.status(),
                internalMessage,
                ex.request().httpMethod().name(),
                ex.request().url()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(FeignException.Conflict.class)
    public ResponseEntity<FeignErrorResponse> handleFeignConflict(FeignException.Conflict ex) {
        String internalMessage = FeignErrorMessageHelper.extractErrorMessage(ex);
        FeignErrorResponse errorResponse = new FeignErrorResponse(
                ex.status(),
                internalMessage,
                ex.request().httpMethod().name(),
                ex.request().url()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(FeignException.InternalServerError.class)
    public ResponseEntity<FeignErrorResponse> handleFeignInternalServerError(FeignException.InternalServerError ex) {
        String internalMessage = FeignErrorMessageHelper.extractErrorMessage(ex);
        FeignErrorResponse errorResponse = new FeignErrorResponse(
                ex.status(),
                internalMessage,
                ex.request().httpMethod().name(),
                ex.request().url()
        );

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<FeignErrorResponse> handleFeignException(FeignException ex) {
        String internalMessage = FeignErrorMessageHelper.extractErrorMessage(ex);
        FeignErrorResponse errorResponse = new FeignErrorResponse(
                ex.status(),
                internalMessage,
                ex.request().httpMethod().name(),
                ex.request().url()
        );

        return ResponseEntity.status(HttpStatus.valueOf(ex.status())).body(errorResponse);
    }

}
