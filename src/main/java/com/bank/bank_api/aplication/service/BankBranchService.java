package com.bank.bank_api.aplication.service;

import com.bank.bank_api.aplication.ports.in.CreateBankBranchUseCase;
import com.bank.bank_api.aplication.ports.in.DeleteBankBranchUseCase;
import com.bank.bank_api.aplication.ports.out.BankBranchRepositoryPort;
import com.bank.bank_api.domain.entities.BankBranch;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BankBranchService implements CreateBankBranchUseCase, DeleteBankBranchUseCase {

    private final BankBranchRepositoryPort bankBranchRepositoryPort;

    @Override
    public BankBranch create(BankBranch bankBranch) {
        log.info("BankBranchService.create ...");
        return bankBranchRepositoryPort.save(bankBranch);
    }

    @Override
    public void delete(String uid) {
        log.info("BankBranchService.delete uid={}", uid);
        bankBranchRepositoryPort.delete(uid);
    }
}
