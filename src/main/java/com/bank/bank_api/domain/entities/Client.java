package com.bank.bank_api.domain.entities;

public record Client(
        String uid,
        BankBranch branch,
        String name,
        String address,
        String du
) {
}
