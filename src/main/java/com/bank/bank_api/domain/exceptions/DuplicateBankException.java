package com.bank.bank_api.domain.exceptions;

public class DuplicateBankException extends RuntimeException {
    public DuplicateBankException(String message) {
        super(message);
    }
}
