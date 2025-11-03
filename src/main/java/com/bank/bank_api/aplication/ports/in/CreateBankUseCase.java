package com.bank.bank_api.aplication.ports.in;

import com.bank.bank_api.domain.entities.Bank;

public interface CreateBankUseCase {

    Bank create(Bank bank);

}
