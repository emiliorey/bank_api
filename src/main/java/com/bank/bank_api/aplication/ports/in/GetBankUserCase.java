package com.bank.bank_api.aplication.ports.in;

import com.bank.bank_api.domain.entities.Bank;

import java.util.Optional;

public interface GetBankUserCase {

    Optional<Bank> findByUid(String uid);
    Bank getByUid(String uid);

}
