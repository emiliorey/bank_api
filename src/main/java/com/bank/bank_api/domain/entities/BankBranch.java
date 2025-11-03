package com.bank.bank_api.domain.entities;

public record BankBranch(
        String uid,
        Bank bank,
        String name,
        String address
) {
}
