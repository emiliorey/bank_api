package com.bank.bank_api.infraestructure.validators.bank;

import com.bank.bank_api.domain.entities.Bank;
import com.bank.bank_api.infraestructure.repositories.persistence.repositories.BankRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BankExistValidator {

    private final BankRepository bankRepository;

    public boolean validate(Bank bank) {
       return bankRepository.existsByNameAndAddress(bank.name(), bank.address());
    }
}
