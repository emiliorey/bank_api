package com.bank.bank_api.aplication.ports.out;

import com.bank.bank_api.domain.entities.Bank;

import java.util.Optional;

public interface BankRepositoryPort {

    Bank save(Bank bank);
    Optional<Bank> findByUid(String UUID);
    Bank getByUid(String UUID);
    Bank update(String uid, Bank bank);
}
