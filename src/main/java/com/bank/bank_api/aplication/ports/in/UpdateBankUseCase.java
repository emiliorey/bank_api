package com.bank.bank_api.aplication.ports.in;

import com.bank.bank_api.domain.entities.Bank;

public interface UpdateBankUseCase {

    Bank update(String uid, Bank bank);
}
