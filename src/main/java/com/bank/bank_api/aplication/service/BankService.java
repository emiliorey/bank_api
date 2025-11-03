package com.bank.bank_api.aplication.service;

import com.bank.bank_api.aplication.ports.in.CreateBankUseCase;
import com.bank.bank_api.aplication.ports.in.GetBankUserCase;
import com.bank.bank_api.aplication.ports.out.BankRepositoryPort;
import com.bank.bank_api.domain.entities.Bank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BankService implements CreateBankUseCase, GetBankUserCase {

    private final BankRepositoryPort bankRepositoryPort;

    @Override
    public Bank create(Bank bank) {
        log.info("BankService.create ....");
        return bankRepositoryPort.save(bank);
    }

    @Override
    public Optional<Bank> findByUid(String UUID) {
        log.info("BankService.findByUid ....");
        return bankRepositoryPort.findByUid(UUID);
    }

    @Override
    public Bank getByUid(String UUID) {
        log.info("BankService.getByUid ....");
        return bankRepositoryPort.getByUid(UUID);
    }

}
