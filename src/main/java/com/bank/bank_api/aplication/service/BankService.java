package com.bank.bank_api.aplication.service;

import com.bank.bank_api.aplication.ports.in.CreateBankUseCase;
import com.bank.bank_api.aplication.ports.in.GetBankUserCase;
import com.bank.bank_api.aplication.ports.in.UpdateBankUseCase;
import com.bank.bank_api.aplication.ports.out.BankRepositoryPort;
import com.bank.bank_api.domain.entities.Bank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BankService implements CreateBankUseCase, GetBankUserCase, UpdateBankUseCase {

    private final BankRepositoryPort bankRepositoryPort;

    @Override
    public Bank create(Bank bank) {
        log.info("BankService.create ....");
        return bankRepositoryPort.save(bank);
    }

    @Override
    public Optional<Bank> findByUid(String uid) {
        log.info("BankService.findByUid ....");
        return bankRepositoryPort.findByUid(uid);
    }

    @Override
    public Bank getByUid(String uid) {
        log.info("BankService.getByUid ....");
        return bankRepositoryPort.getByUid(uid);
    }

    @Override
    public Bank update(String uid, Bank bank) {
        log.info("BankService.getByUid ....");
        return bankRepositoryPort.update(uid, bank);
    }

}
